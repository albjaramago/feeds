package com.csdm.feeds;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.csdm.feeds.exception.MessageException;
import com.csdm.feeds.model.News;
import com.csdm.feeds.repository.NewsRepository;
import com.csdm.feeds.service.FeedsService;
import com.csdm.params.TaskParam;

@Component
public class NewsFeedTask {

	
	@Autowired
	FeedsService feedsService;
	
	@Autowired
	NewsRepository newsRepository;
	

	/**
	 * Task execution to pull news
	 * 
	 */
	@Scheduled(fixedRate = TaskParam.TASK_PERIOD)
	public void execute() {
		
		final long startTime = System.currentTimeMillis();

		// Pull news through feedsService.
		List<News> newsEntityList = feedsService.pullNews();

		// filter the items that were saved in previous execution of task
		if (newsRepository.count()>0) {
			newsEntityList = newsEntityList.stream().filter(
					news -> news.getPublication().getTime() > startTime - TaskParam.TASK_PERIOD)
					.collect(Collectors.toList());
		}
		// Save news entity list.
		if (!newsEntityList.isEmpty()) {
			
			try{
				newsRepository.saveAll(newsEntityList);
			}catch(Exception e){
				throw new RuntimeException(MessageException.SAVE_NEWS.getValue());
			}
		}

	}

}
