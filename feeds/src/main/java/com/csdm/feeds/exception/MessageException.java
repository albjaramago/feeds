package com.csdm.feeds.exception;

public enum MessageException {

	
	/**
	 * SAVE_NEWS
	 */
	SAVE_NEWS ("Saving News Error"),
	
	/**
	 * UNMARSHAL_RSS
	 */
	UNMARSHAL_RSS ("Unmarshalling Rss Error"),
	
	/**
	 * HTTP_RESPONSE
	 */
	HTTP_RESPONSE ("Failed : HTTP error code : "),
	
	
	/**
	 * PARSE_PUBLICATION_DATE
	 */
	PARSE_PUBLICATION_DATE ("Parsing Publication Date Error");
	
	private String value;
	
	private MessageException(String value) {
		
		this.value = value;
	}
	
	
	public String getValue() {
		
		return this.value;
	}
}
