package com.csdm.feeds.service.impl;

import java.io.StringReader;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.springframework.stereotype.Service;

import com.csdm.feeds.dto.Rss;
import com.csdm.feeds.exception.MessageException;
import com.csdm.feeds.model.News;
import com.csdm.feeds.dto.Item;
import com.csdm.feeds.service.FeedsService;
import com.csdm.params.FeedsServiceParam;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@Service
public class FeedsServiceImpl implements FeedsService {

	/*
	 * @see com.csdm.feeds.service.FeedsService#pullNews()
	 */
	@Override
	public List<News> pullNews() {

		// Client response.
		String newsString = this.getClientResponse();

		System.out.println(newsString);

		// Unmarshal news into Rss class.
		Rss rss = null;
		try {
			rss = getUnmarshalledResponse(newsString);
		} catch (JAXBException e1) {
			throw new RuntimeException(MessageException.UNMARSHAL_RSS.getValue());
		}

		//Get a list of entity News from items in Rss
		List<News> newsEntityList = new ArrayList<News>();
		if (rss != null) {
			newsEntityList = this.geNewsEntityList(rss);
		}

		return newsEntityList;

	}

	/**
	 * 
	 * getClientResponse
	 * 
	 * @return String
	 */
	private String getClientResponse() {

		Client client = Client.create();

		WebResource webResource = client.resource(FeedsServiceParam.RESOURCE_URL.getValue());

		ClientResponse response = webResource.accept(FeedsServiceParam.RESOURCE_TYPE.getValue()).get(ClientResponse.class);

		if (response.getStatus() != 200) {
			throw new RuntimeException(MessageException.HTTP_RESPONSE.getValue() + response.getStatus());
		}

		return response.getEntity(String.class);

	}

	/**
	 * getUnmarshalledResponse
	 * 
	 * @param newsString
	 * @return
	 * @throws JAXBException
	 */
	private Rss getUnmarshalledResponse(String newsString) throws JAXBException {

		JAXBContext jaxbContext = JAXBContext.newInstance(Rss.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

		StringReader reader = new StringReader(newsString);
		Rss rss = (Rss) unmarshaller.unmarshal(reader);

		return rss;
	}

	/**
	 * 
	 * geNewsEntityList
	 * 
	 * @param rss
	 * @return
	 */
	private List<News> geNewsEntityList(Rss rss) {

		List<News> newsEntityList = new ArrayList<News>();

		//Sort by publication date descendent
		List<Item> items = rss.getChannel().getItems().stream()
				.sorted((i1, i2) -> Long.compare(this.getTimestamp(i1.getPubDate()).getTime(),
						this.getTimestamp(i2.getPubDate()).getTime()))
				.collect(Collectors.toList());

		// Recover from each item: title, description, publicationDate, image.
		items.forEach(item -> {
	
			News newsEntity = new News();

			if (item.getTitle() != null)
				newsEntity.setTitle(item.getTitle());
			if (item.getDescription() != null)
				newsEntity.setDescription(item.getDescription());
			if (this.getTimestamp(item.getPubDate()) != null)
				newsEntity.setPublication(this.getTimestamp(item.getPubDate()));
			if (item.getEnclosure() != null && item.getEnclosure().getUrl() != null)
				newsEntity.setImage(item.getEnclosure().getUrl());

			newsEntityList.add(newsEntity);

		});

		
		
		return newsEntityList;

	}
	
	
	/**
	 * 
	 * getTimestamp
	 * 
	 * @param stringPubDate
	 * @return
	 */
	private Timestamp getTimestamp(String stringPubDate) {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat(FeedsServiceParam.DATE_FORMAT.getValue(), Locale.ENGLISH);

		Date parsedDate;
		Timestamp timestampPubDate = null;
		try {
			parsedDate = dateFormat.parse(stringPubDate);
			timestampPubDate = new Timestamp(parsedDate.getTime());
		} catch (ParseException e) {
			throw new RuntimeException(MessageException.PARSE_PUBLICATION_DATE.getValue());
		}
		
		return timestampPubDate;
	}

}
