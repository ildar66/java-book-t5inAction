package com.tapestry5book.tlog.tags.test;


import com.tapestry5book.tlog.core.entities.Tag;
import com.tapestry5book.tlog.tags.services.TagsModule;
import org.apache.tapestry5.dom.Document;
import org.apache.tapestry5.hibernate.HibernateSessionManager;
import org.apache.tapestry5.test.PageTester;
import org.apache.tapestry5.test.TapestryTestCase;
import org.example.testapp.pages.SidebarBlocksDemo;
import org.hibernate.Session;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SidebarBlocksTest extends TapestryTestCase {

    private PageTester tester;

    @BeforeMethod
    public void setUp() {
        tester = new PageTester("org.example.testapp", "app", "src/test/webapp", TagsModule.class);
    }


    @Test
    public void test() {
        HibernateSessionManager sessionManager = tester.getService(HibernateSessionManager.class);

        Session session = sessionManager.getSession();

        Tag tag = new Tag();
        tag.setName("Foo");

        Tag anotherTag = new Tag();
        anotherTag.setName("Bar");

        session.save(tag);

        session.save(anotherTag);

        sessionManager.commit();

        Document document = tester.renderPage(SidebarBlocksDemo.class.getSimpleName());

        String markup = document.toString();

        assertTrue(markup.contains("Foo"));

        assertTrue(markup.contains("Bar"));
    }
}
