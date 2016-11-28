package com.marteksolution.cooccurrence.webcrawler;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.marteksolution.cooccurrence.analyze.Report;
import com.marteksolution.cooccurrence.analyze.SentenceReport;
import com.marteksolution.cooccurrence.analyze.TextAnalyzer;
import com.marteksolution.cooccurrence.database.entity.Occurrence;
import com.marteksolution.cooccurrence.database.entity.Sentence;
import com.marteksolution.cooccurrence.database.entity.SentenceOccurrence;
import com.marteksolution.cooccurrence.database.entity.SentenceOccurrenceId;
import com.marteksolution.cooccurrence.database.entity.Url;
import com.marteksolution.cooccurrence.database.repository.OccurrenceRepository;
import com.marteksolution.cooccurrence.database.repository.SentenceOccurrenceRepository;
import com.marteksolution.cooccurrence.database.repository.SentenceRepository;
import com.marteksolution.cooccurrence.database.repository.UrlRepository;
import com.marteksolution.cooccurrence.webcrawler.exception.PageNotInRightLanguageException;

import lombok.extern.slf4j.Slf4j;

/**
 * Web crawler that finds word occurrences.
 * 
 * @author Pierre Martin
 *
 */
@Slf4j
@Service
public class WebCrawler {

	@Autowired
	private UrlRepository urlRepository;
	
	@Autowired
	private SentenceRepository sentenceRepository;
	
	@Autowired 
	private OccurrenceRepository occurrenceRepository;
	
	@Autowired 
	private SentenceOccurrenceRepository sentenceOccurrenceRepository;
	
	@Autowired TextAnalyzer textAnalyzer;
	
	private boolean stop = false;

	/**
	 * Start crawling in a new thread.
	 * 
	 * @return the asynch response. Never null. 
	 */
	@Async
	public void start() {
		Url url = null;
		while (true) {
			try {
				url = urlRepository.findFirstByDateScannedOrderByIdAsc(null);
				if (url == null) {
					while (url == null) {
						url = WebSiteLoader.getRandomUrl();

						if (urlExists(url)) {
							url = null;
						}
					}
					url = urlRepository.saveAndFlush(url);
				}
				log.info("Crawling URL : " + url);

				WebPage webPage = WebSiteLoader.readPage(url);
				if (webPage != null) {
					Report report = textAnalyzer.analyze(webPage.getText());
					
					for(SentenceReport sentenceReport : report.getSentenceReports()){
						Sentence sentence = sentenceReport.getSentence();
						sentence = sentenceRepository.saveAndFlush(sentence);
						sentenceReport.setSentence(sentence);
						
						for(Occurrence occurrence : sentenceReport.getOccurences()){
							occurrence = occurrenceRepository.saveAndFlush(occurrence);
							
							SentenceOccurrence so = new SentenceOccurrence();
							SentenceOccurrenceId soId = new SentenceOccurrenceId();
							so.setSentenceOccurrenceId(soId);
							soId.setOccurenceId(occurrence.getId());
							soId.setSentenceId(sentence.getId());
							
							sentenceOccurrenceRepository.saveAndFlush(so);
						}
					}
					
					addUrls(webPage.getUrls());
				}

				log.info("Done crawling URL : " + url);
			} catch (PageNotInRightLanguageException ex) {
				log.info("Page not in the right language : " + ex.getText());
			} catch (Exception ex) {
				log.error("Error while crawling : " + url, ex);
			}
			
			url.setDateScanned(new Date());
			urlRepository.saveAndFlush(url);
			
			if(stop){
				break;
			}
		}
	}
	
	public void stop(){
		this.stop = true;
	}
	
	private void addUrls(Set<String> urls) {
		Map<String, Integer> hostnameCount = countByHostNames();

		for (String url : urls) {
			try {
				URI uri = new URI(url);
				String host = uri.getHost();
				Integer count = hostnameCount.get(host);
				if (count == null) {
					count = 0;
				}
				
				if(count < 20){
					Url entity = new Url();
					entity.setDescription(url);
					urlRepository.saveAndFlush(entity);
					hostnameCount.put(host, count + 1);
				}

			} catch (Exception e) {
				log.warn("Invalid URL or already in DB.", e);
			}
		}
	}
	

	private Map<String, Integer> countByHostNames() {
		List<Url> urls = urlRepository.findByDateScanned(null);
		
		Map<String, Integer> numberOfHostname = new HashMap<String, Integer>();
		for (Url url : urls) {
			try {
				URI uri = new URI(url.getDescription());
				String host = uri.getHost();
				Integer num = numberOfHostname.get(host);
				if (num == null) {
					num = 0;
				}
				numberOfHostname.put(host, num + 1);

			} catch (URISyntaxException e) {
				log.error("Invalid URL", e);
			}
		}
		return numberOfHostname;
	}
	
	private boolean urlExists(Url url){
		if(url != null){
			Url existing = urlRepository.findByDescription(url.getDescription());
			if(existing != null){
				return true;
			}
		}
		return false;
	}
}
