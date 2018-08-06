package com.csdm.feeds.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class Channel {


	@XmlElement(name="item")
	private List<Item> items;


	
	public List<Item> getItems() {
		return items;
	}

	public void setItem(List<Item> items) {
		this.items = items;
	}


}
