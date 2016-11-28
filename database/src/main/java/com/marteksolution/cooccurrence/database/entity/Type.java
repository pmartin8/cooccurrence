package com.marteksolution.cooccurrence.database.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

/**
 * Type generated by hbm2java
 */
@Entity
@Data
public class Type implements Serializable {

	private static final long serialVersionUID = 7388443870916348388L;

	@Id
	@GeneratedValue
	private Integer id;

	@Column(name = "description", length = 45, nullable = false)
	private String description;
}