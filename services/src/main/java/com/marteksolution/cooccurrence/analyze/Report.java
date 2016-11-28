package com.marteksolution.cooccurrence.analyze;

import java.util.List;

import lombok.Data;

/**
 * Text analysis report.
 * 
 * @author Pierre Martin
 *
 */
@Data
public class Report {

	private List<SentenceReport> sentenceReports;
}
