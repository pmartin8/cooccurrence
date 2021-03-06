package com.marteksolution.cooccurrence.database.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

/**
 * Url generated by hbm2java
 */
@Entity
@Data
public class Url implements Serializable {

	private static final long serialVersionUID = 5547391703943080314L;

	@Id
	@GeneratedValue
	private Integer id;

	@Column(name = "description", length = 1024, nullable = false)
	private String description;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dateScanned", nullable = true)
	private Date dateScanned;

}
