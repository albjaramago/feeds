package com.csdm.feeds.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.csdm.feeds.model.News;
import com.csdm.feeds.repository.NewsRepository;

@RestController
@RequestMapping("/feed")
public class FeedRest {

	
	@Autowired
	NewsRepository newsRepository;
	
	
	/**
	 * 
	 * GET news
	 * 
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value="/news", method=RequestMethod.GET)
	public Page<News> getAllNews(Pageable pageable) {
	    
		return newsRepository.findAll(pageable);
	}
}
