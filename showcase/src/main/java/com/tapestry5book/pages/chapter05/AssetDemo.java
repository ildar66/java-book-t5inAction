package com.tapestry5book.pages.chapter05;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

public class AssetDemo {
	@Inject	@Path("context:images/logo.gif")
	@Property
	private Asset logo;
	
	@Inject	@Path("classpath:/css/main.css")
	@Property
	private Asset style;
	
	@Inject	@Path("js/lib.js")
	@Property
	private Asset jsLib;
}
