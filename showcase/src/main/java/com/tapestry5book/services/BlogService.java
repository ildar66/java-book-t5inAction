package com.tapestry5book.services;

import com.tapestry5book.entities.Article;
import com.tapestry5book.entities.Blog;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;

import java.util.List;

public interface BlogService {

	Blog findBlog();

	List<Article> findRecentArticles();

	Article findArticleById(Long id);

	// Declarative transaction managemen
	@CommitAfter
	void persistArticle(Article article);
}
