package com.tapestry5book.components;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.BeginRender;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.ioc.annotations.Inject;

public class Duke {

	@Inject
	@Path("duke.jpg")
	private Asset duke;

	/*
	 * Returning false aborts the normal flow, causing a transition to a complementary phase. For example,
	 * returning false in BeginRender results in a transition to CleanupRender; all the phases in between are skipped.
	 * 
	 * Instead of returning a boolean value, a render phase method may be void.
	 * A void method is equivalent to a method returning true, and so doesn't skip the succeeding phase.
	 * 
	 * Yet another equivalent for returning true is to change method's return type to Object and return null
	 */
	@BeginRender
	boolean renderImage(MarkupWriter writer) {
		writer.element("img", "src", duke);

		writer.end();

		return false;
	}
}
