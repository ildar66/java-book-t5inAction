package com.tapestry5book.pages.chapter03.blog.v2;

import com.tapestry5book.entities.Article;
import org.apache.tapestry5.annotations.Property;

public class Read {

	@Property
	private Article article;

	void onActivate(Article article) {
		this.article = article;
	}

	Article onPassivate() {
		return article;
	}

/*
 * Overloaded activation methods
 * 
 * 	@Property
	private boolean showComments;

	void onActivate(Article article) {
		onActivate(article, true);
	}

	boolean onActivate(Article article, boolean showComments) {
		this.article = article;
		this.showComments = showComments;
		return true;// nod need call another onActivate(Article article)
	}

	Object[] onPassivate() {
		return new Object[] { article, showComments };
	}*/
}