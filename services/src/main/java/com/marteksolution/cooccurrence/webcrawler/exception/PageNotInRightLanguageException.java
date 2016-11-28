package com.marteksolution.cooccurrence.webcrawler.exception;

/**
 * Exception trhown when a webpage is not in the right language.
 * 
 * @author Pierre Martin
 *
 */
public class PageNotInRightLanguageException extends RuntimeException {

	private static final long serialVersionUID = 4313653905671020839L;

	private String text;

	public PageNotInRightLanguageException(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "PageNotInRightLanguageException [text=" + text + "]";
	}

}
