package com.csdm.feeds.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.csdm.feeds.model.News;

public interface NewsRepository extends PagingAndSortingRepository<News,Long> {
	
}
