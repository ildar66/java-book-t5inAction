package com.tapestry5book.pages.chapter12;

import com.tapestry5book.entities.Article;
import com.tapestry5book.services.BlogService;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.List;

public class ShowArticles2 {

    @Inject
    private BlogService blogService;

    @Property
    private List<Article> articles;

    @Property
    private Article currentArticle;

    void onActivate() {
        articles = blogService.findRecentArticles();
    }
}