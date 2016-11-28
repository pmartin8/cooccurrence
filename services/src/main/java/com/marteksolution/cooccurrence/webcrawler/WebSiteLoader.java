package com.marteksolution.cooccurrence.webcrawler;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.Connection.Response;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.marteksolution.cooccurrence.database.entity.Url;

/**
 * Class to download a web page and read its content.
 *
 * @author Pierre Martin
 */
public class WebSiteLoader {

	private static final Logger LOGGER = org.apache.log4j.Logger.getLogger(WebSiteLoader.class);

	private static final String URL_RANDOM = "http://fr.wikipedia.org/wiki/Special:Random/";
	private static final String USER_AGENT = "ExampleBot 1.0 (+http://example.com/bot)";
	private static final int TIMEOUT = 30 * 1000;

	/**
	 * Look for a word in the dictionary
	 * 
	 * @param word a word. Not null.
	 * @return All possible words. 
	 * @throws IOException
	 */
	public static String[] lookupWord(String word) throws IOException {
		String google = "http://www.larousse.fr/dictionnaires/francais/";
		String charset = "utf-8";
		String userAgent = "ExampleBot 1.0 (+http://example.com/bot)";
		String wordNoAccents = removeAccents(word);
		try {
			Elements elements = Jsoup.connect(google + URLEncoder.encode(wordNoAccents, charset)).userAgent(userAgent)
					.get().select("#cphContentMaster_Wrapper>article>header>h2");
			for (Iterator<Element> it = elements.iterator(); it.hasNext();) {
				Element element = it.next();
				if (element.text() != null || element.text().length() > 0) {
					return element.text().substring(1).split(", ");
				}
			}

		} catch (HttpStatusException ex) {
			return null;
		}

		return null;
	}

	private static String removeAccents(String str) {
		str = Normalizer.normalize(str, Normalizer.Form.NFD);
		str = str.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
		return str;
	}

	public static List<String> getDefinitions(String word) {
		List<String> definitions = new ArrayList<String>();

		String google = "http://www.larousse.fr/dictionnaires/francais/";
		String charset = "utf-8";
		String userAgent = "ExampleBot 1.0 (+http://example.com/bot)";
		String wordNoAccents = removeAccents(word);
		try {
			Elements elements = Jsoup.connect(google + URLEncoder.encode(wordNoAccents, charset)).userAgent(userAgent)
					.timeout(30 * 1000).get().select(".DivisionDefinition");
			for (Iterator<Element> it = elements.iterator(); it.hasNext();) {
				Element element = it.next();
				definitions.add(element.text());
			}

		} catch (Exception ex) {
			return definitions;
		}

		return definitions;
	}

	/**
	 * Get a random URL that exists. 
	 * 
	 * @return The URL. Never null. 
	 */
	public static Url getRandomUrl() {
		String userAgent = "ExampleBot 1.0 (+http://example.com/bot)";
		try {
			Response response = Jsoup.connect(URL_RANDOM).userAgent(userAgent).timeout(TIMEOUT).followRedirects(true)
					.execute();
			Url url = new Url();
			url.setDescription(response.url().toString());
			return url;
		} catch (Exception ex) {
			LOGGER.error(ex);
		}
		return null;
	}

	/**
	 * Read a webpage from a URL. 
	 * 
	 * @param pUrl not null. 
	 * @return the information from the web page.
	 */
	public static WebPage readPage(Url pUrl) {
		WebPage webPage = new WebPage();
		webPage.setUrls(new HashSet<String>());
		
		String url = pUrl.getDescription();
		
		String host = getHost(url);
		try {
			Document doc = Jsoup.connect(url).userAgent(USER_AGENT).timeout(TIMEOUT).get();
			Elements links = doc.select("a[href]");
			for (Element link : links) {
				String strLink = link.attr("href").toLowerCase().trim();
				if (strLink.startsWith("http")) {
					webPage.getUrls().add(strLink.trim());
					webPage.getUrls().add(strLink);
				} else if (strLink.startsWith("/") || strLink.startsWith("\\")) {
					strLink = host + strLink;
					webPage.getUrls().add(strLink);
				} else if(!strLink.startsWith("#")) {
					int index = Math.max(url.lastIndexOf("/"), url.lastIndexOf("\\"));
					strLink = url.substring(0, index) + strLink;
					webPage.getUrls().add(strLink);
				}
			}
			webPage.setText(doc.text());

			return webPage;
		} catch (Exception ex) {
			LOGGER.error(ex);
		}

		return null;
	}

	private static String getHost(String url) {
		try {
			URI uri = new URI(url);
			return uri.getScheme()  + "://" + uri.getHost();
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}
}
