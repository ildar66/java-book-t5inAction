package com.tapestry5book.services.impl;

import com.tapestry5book.entities.Article;
import com.tapestry5book.services.BlogService;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public class BlogBean {

    @Autowired
    @Inject
    private BlogService blogService;

    @Value("${tapestry.production-mode}")
    private boolean productionMode;

    public List<Article> getArticles() {
        return blogService.findRecentArticles();
    }

    public boolean isProductionMode() {
        return productionMode;
    }
}
