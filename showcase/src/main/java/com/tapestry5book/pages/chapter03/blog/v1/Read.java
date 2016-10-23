package com.tapestry5book.pages.chapter03.blog.v1;

import com.tapestry5book.entities.Article;
import com.tapestry5book.services.BlogService;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

public class Read {
	@Inject
	private BlogService blogService;

	@Property
	private Article article;

	void onActivate(Long articleId) {
		this.article = blogService.findArticleById(articleId);
	}

	Long onPassivate() {
		return article.getId();
	}

//	@Property
//	private boolean showComments;
//
//	void onActivate(Long articleId, boolean showComments) {
//		this.article = blogService.findArticleById(articleId);
//		this.showComments = showComments;
//	}
//
//	Object[] onPassivate() {
//		return new Object[] { article, showComments };
//	}
}
