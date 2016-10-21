package com.tapestry5book.pages;

import org.apache.tapestry5.MarkupWriter;

public class PageWithoutTemplate {
	void beginRender(MarkupWriter writer) {
		writer.element("html");
		writer.element("body");
		writer.writeRaw("Hello, World from PageWithoutTemplate!");
		writer.end();
		writer.end();
	}
}