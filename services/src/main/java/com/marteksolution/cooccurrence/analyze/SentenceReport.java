package com.marteksolution.cooccurrence.analyze;

import java.util.List;

import com.marteksolution.cooccurrence.database.entity.Occurrence;
import com.marteksolution.cooccurrence.database.entity.Sentence;

import lombok.Data;

/**
 * Report on a single sentence.
 * 
 * @author Pierre Martin
 *
 */
@Data
public class SentenceReport {

	private Sentence sentence;
	private List<Occurrence> occurences;
	private List<String> unknownWords;
}
