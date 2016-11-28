package com.marteksolution.cooccurrence.analyze;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marteksolution.cooccurrence.database.entity.Occurrence;
import com.marteksolution.cooccurrence.database.entity.Sentence;
import com.marteksolution.cooccurrence.database.entity.Word;
import com.marteksolution.cooccurrence.database.repository.WordRepository;
import com.marteksolution.cooccurrence.webcrawler.exception.PageNotInRightLanguageException;
import com.marteksolution.cooccurrence.webcrawler.exception.SentenceNotInRightLanguageException;

import lombok.extern.slf4j.Slf4j;

/**
 * Analyzer that finds occurence of words in a text.
 * 
 * @author Pierre Martin
 *
 */
@Service
@Slf4j
public class TextAnalyzer {

	private static Pattern WORD_PATTERN = Pattern.compile("\\p{Alpha}{2,}");
	
	@Autowired
	private WordRepository wordRepository;

	public Report analyze(String text) {
		Report report = new Report();
		report.setSentenceReports(new ArrayList<SentenceReport>());

		List<String> sentencesText = breakIntoSentences(text);

		int wrongSentences = 0;
		int rightSentences = 0;
		for (String sentenceText : sentencesText) {
			try {
				analyzeSentence(sentenceText, report);
				rightSentences++;
			} catch (SentenceNotInRightLanguageException ex) {
				wrongSentences++;
				log.debug("Sentence not in the right language", ex);
			} catch (Exception ex) {
				log.error("Error analyzing the sentence : " + sentenceText, ex);
			}

			if (wrongSentences + rightSentences > 5) {
				if (wrongSentences > rightSentences) {
					throw new PageNotInRightLanguageException(text);
				}
			}
		}

		return report;
	}

	private void analyzeSentence(String sentenceText, Report report) {
		SentenceReport sentenceReport = new SentenceReport();
		sentenceReport.setSentence(new Sentence());
		sentenceReport.setOccurences(new ArrayList<Occurrence>());
		sentenceReport.setUnknownWords(new ArrayList<String>());

		report.getSentenceReports().add(sentenceReport);

		sentenceReport.getSentence().setDescription(sentenceText);

		findOccurrences(sentenceReport);
	}

	private void findOccurrences(SentenceReport sentenceReport) {
		List<String> wordsStr = splitIntoWords(sentenceReport.getSentence().getDescription());
		List<Word> words = getWords(wordsStr, sentenceReport);

		if (sentenceReport.getUnknownWords().size() > wordsStr.size() / 2) {

			// We don't want to compute this sentence.
			throw new SentenceNotInRightLanguageException(sentenceReport.getSentence().getDescription());
		}

		for (int i = 0; i < words.size(); i++) {
			Word word1 = words.get(i);
			if (word1.getId() == null) {
				continue;
			}
			for (int j = i + 1; j < words.size(); j++) {
				Word word2 = words.get(j);
				if (word2.getId() == null || word1.getId() == word2.getId()) {
					continue;
				}
				Occurrence occurrence = new Occurrence();
				occurrence.setWordByWord1id(word1);
				occurrence.setWordByWord2id(word2);
				occurrence.setScore(getScore(j - i));
				sentenceReport.getOccurences().add(occurrence);
			}
		}
	}

	private int getScore(int distance) {
		if (distance == 1) {
			return 10;
		}

		if (distance < 4) {
			return 7;
		}

		if (distance < 7) {
			return 5;
		}

		if (distance < 10) {
			return 2;
		}

		return 0;
	}

	private List<Word> getWords(List<String> wordsStr, SentenceReport sentenceReport) {
		List<Word> words = new ArrayList<Word>();
		for (String wordStr : wordsStr) {
			Word word = wordRepository.findByDescription(wordStr);
			if(word == null){
				word = new Word();
				word.setDescription(wordStr);
				sentenceReport.getUnknownWords().add(wordStr);
			}
			words.add(word);
		}
		return words;
	}

	private List<String> splitIntoWords(String sentenceText) {
		String[] words = sentenceText.split("[ \\.'-,]");

		List<String> list = new ArrayList<String>();
		for (String word : words) {
			if (isWord(word)) {
				list.add(word.trim().toLowerCase());
			}
		}
		return list;
	}

	private boolean isWord(String word) {
		Matcher matcher = WORD_PATTERN.matcher(stripAccents(word));
		return matcher.matches();
	}

	private String stripAccents(String s) {
		s = Normalizer.normalize(s, Normalizer.Form.NFD);
		s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
		return s;
	}

	private List<String> breakIntoSentences(String text) {
		List<String> sentences = new ArrayList<String>();

		String[] splits = text.split("[\\.;:/\\\\]");
		for (String txt : splits) {
			sentences.add(txt.trim());
		}
		return sentences;
	}

}
