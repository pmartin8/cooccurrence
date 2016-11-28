package com.marteksolution.cooccurrence.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marteksolution.cooccurrence.api.WebCrawlerService;
import com.marteksolution.cooccurrence.webcrawler.WebCrawler;

/**
 * Service to interact with the web crawler.
 * 
 * @author Pierre Martin
 *
 */
@Service
public class WebCrawlerServiceImpl implements WebCrawlerService {
	
	@Autowired
	private WebCrawler webCrawler;


	/** 
	 * {@inheritDoc}
	 */
	public String start() {
		webCrawler.start();
		return "started";
	}
	
	/**
	 * {@inheritDoc}
	 */
	public String stop(){
		webCrawler.stop();
		return "stopped";
	}
}
