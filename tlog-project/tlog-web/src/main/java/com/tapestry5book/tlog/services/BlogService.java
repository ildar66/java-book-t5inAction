package com.tapestry5book.tlog.services;

import com.tapestry5book.tlog.core.entities.*;

/**
 * This service is a typical DAO (Data Access Object) with methods to find, save and delete entities. DAO is a
 * standard JEE pattern for abstraction of accessing data(Domain model for the Blog application) from a
 * database.
 */
public interface BlogService {

	User findUserByName(String name);

	Blog findBlog();

	PageableLoopDataSource findRecentArticles();

	PageableLoopDataSource findArticles(Month month);

	PageableLoopDataSource findArticles(Tag tag);

	PageableLoopDataSource findArticles(String term);
}
