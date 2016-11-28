package com.marteksolution.cooccurrence.webcrawler;

import java.util.Set;

import lombok.Data;

/**
 * The extracted information from a web page.
 * 
 * @author Pierre Martin
 *
 */
@Data
public class WebPage {

	private String text;
	private Set<String> urls;
	private Set<String> unkownWords;
}
