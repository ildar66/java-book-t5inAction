package com.tapestry5book.tlog.services.impl;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ApplicationStateManager;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.tapestry5book.tlog.core.entities.Article;
import com.tapestry5book.tlog.core.entities.Blog;
import com.tapestry5book.tlog.core.entities.Month;
import com.tapestry5book.tlog.core.entities.Tag;
import com.tapestry5book.tlog.core.entities.User;
import com.tapestry5book.tlog.services.BlogService;
import com.tapestry5book.tlog.services.PageableLoopDataSource;

public class BlogServiceImpl implements BlogService {

	@Inject
	private Session session;

	@Inject
	private ApplicationStateManager applicationStateManager;

	public Blog findBlog() {
		return (Blog) this.session.createCriteria(Blog.class).uniqueResult();
	}

	public PageableLoopDataSource findRecentArticles() {
		// Access to session object from service layer:
		User authenticatedUser = applicationStateManager.getIfExists(User.class);
		System.out.println("========authenticatedUser==========" + authenticatedUser);// TODO demo temp
		return new PageableLoopDataSourceImpl(session, Article.class);
	}

	public PageableLoopDataSource findArticles(final Month month) {
		return new PageableLoopDataSourceImpl(session, Article.class, new AdditionalConstraintsCallback() {
			public void apply(Criteria criteria) {
				criteria.add(Restrictions.between("publishDate", month.getStart(), month.getEnd()));
			}
		});
	}

	public PageableLoopDataSource findArticles(final Tag tag) {
		return new PageableLoopDataSourceImpl(session, Article.class, new AdditionalConstraintsCallback() {
			public void apply(Criteria criteria) {
				criteria.createCriteria("tags").add(Restrictions.eq("name", tag.getName()));
				published(criteria);
			}
		});
	}

	public User findUserByName(final String name) {
		return (User) session.createCriteria(User.class).add(Restrictions.eq("name", name)).uniqueResult();
	}

	public PageableLoopDataSource findArticles(final String term) {
		return new PageableLoopDataSourceImpl(session, Article.class, new AdditionalConstraintsCallback() {
			public void apply(Criteria criteria) {
				criteria.add(Restrictions.or(like("title", term), like("content", term)));
				published(criteria);
			}
		});
	}

	private void published(Criteria criteria) {
		criteria.add(Restrictions.isNotNull("publishDate"));
	}

	private Criterion like(String property, String value) {
		return Restrictions.ilike(property, "%" + value + "%");
	}

}
