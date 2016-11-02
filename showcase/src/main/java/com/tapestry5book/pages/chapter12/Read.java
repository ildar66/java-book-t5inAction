package com.tapestry5book.pages.chapter12;

import com.tapestry5book.entities.Article;
import org.apache.tapestry5.annotations.PageActivationContext;
import org.apache.tapestry5.annotations.Property;

/**
 * Built-in ValueEncoders for Hibernate entities Using the Hibernate integration library you don't need to
 * contribute ValueEncoders for your Hibernate entities. This is done for you automatically. For every
 * Hibernate entity class, Tapestry contributes a ValueEncoder that converts an entity instance into a
 * client-side value by coercing entity's primary key into a String.
 */
public class Read {

	@PageActivationContext
	@Property
	private Article article;

}