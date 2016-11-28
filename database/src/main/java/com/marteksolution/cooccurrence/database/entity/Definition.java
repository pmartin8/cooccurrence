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
 * Definition generated by hbm2java
 */
@Entity
@Data
public class Definition implements Serializable {

	private static final long serialVersionUID = -919175651352616415L;

	@Id
	@GeneratedValue
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "wordId")
	private Word word;
	
	@Column(name="description", length=1024, nullable=false)
	private String description;

}