package com.csdm.params;

public enum FeedsServiceParam {

	
	/**
	 * RESOURCE_URL
	 */
	RESOURCE_URL ("http://feeds.nos.nl/nosjournaal?format=xml"),
	
	/**
	 * RESOURCE_TYPE
	 */
	RESOURCE_TYPE ("application/xml"),
	
	/**
	 * DATE_FORMAT
	 */
	DATE_FORMAT("EEE, d MMM yyyy HH:mm:ss Z");
	
	
	
	private String value;
	
	private FeedsServiceParam(String value) {
		
		this.value = value;
	}
	
	
	public String getValue() {
		
		return this.value;
	}
}
