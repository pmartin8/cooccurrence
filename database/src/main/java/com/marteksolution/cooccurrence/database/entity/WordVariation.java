package com.marteksolution.cooccurrence.database.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

/**
 * Wordvariation generated by hbm2java
 */
@Entity
@Data
public class WordVariation implements Serializable {

	private static final long serialVersionUID = -7291847558635281418L;
	
	@Id 
	@GeneratedValue
	private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "wordId")
	private Word word;
	
	@Column(name="description", length=63, nullable=false)
	private String description;
	
	@Column(name="weak")
	private boolean weak;

}
