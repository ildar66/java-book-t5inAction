package com.tapestry5book.pages.chapter12;

import com.tapestry5book.entities.Article;
import com.tapestry5book.entities.Blog;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

public class Post {
	@Inject
	private Session session;

	@Inject
	private AlertManager alertManager;

	@Property
	private Article article;

	@SessionState
	@Property
	private Blog blog;

	void onPrepare() {
		article = new Article();
	}

/*
 *  Committing transaction manually
 * 	void onSuccess() {
		try {
			session.save(article);
			session.getTransaction().commit();
		} catch (RuntimeException e) {
			session.getTransaction().rollback();
		}
	}*/
	
/*
 * 	Demonstrates HibernateSessionManager service
 * 	@Inject
	private HibernateSessionManager hibernateSessionManager;

	void onSuccess() {
		try {
			hibernateSessionManager.getSession().save(article);
			hibernateSessionManager.commit();
		} catch (RuntimeException e) {
			hibernateSessionManager.abort();
		}
	}*/
	
	//  Declarative transaction managemen
	@CommitAfter
	void onSuccess() {
		article.setBlog(blog);

		session.save(article);

		alertManager.info("Article saved successfully.");
	}
}