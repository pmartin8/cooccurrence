package com.marteksolution.cooccurrence.database.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;

/**
 * Sentence generated by hbm2java
 */
@Entity
@Data
public class Sentence implements Serializable {

	private static final long serialVersionUID = 7333512941259361660L;

	@Id
	@GeneratedValue
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "languageId")
	private Language language;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "urlId")
	private Url url;

	@Column(name = "description", nullable = false)
	private String description;

	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    @JoinColumn(name="sentenceId")
	private Set<SentenceOccurrence> sentenceOccurrences = new HashSet<SentenceOccurrence>();

}
