package com.marteksolution.cooccurrence.database.entity;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import lombok.Data;

/**
 * SentenceOsccurrence generated by hbm2java
 */
@Entity
@Data
public class SentenceOccurrence implements Serializable {

	private static final long serialVersionUID = 4671745916762053004L;

	@EmbeddedId
	private SentenceOccurrenceId sentenceOccurrenceId;
}