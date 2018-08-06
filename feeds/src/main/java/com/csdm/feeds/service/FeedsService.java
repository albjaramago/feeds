package com.csdm.feeds.service;

import java.util.List;

import com.csdm.feeds.model.News;

public interface FeedsService {

	
	/**
	 * 
	 * Pull news
	 * 
	 * @return String
	 */
	public List<News> pullNews ();
}
