package com.tapestry5book.tlog.core.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * A comment is represented by the Comment class and a tag by the Tag class for @ see {@link Article}.
 */

@Entity
public class Tag {
	@Id
	@GeneratedValue
	private Long id;

	@Column
	private String name;

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}
}
