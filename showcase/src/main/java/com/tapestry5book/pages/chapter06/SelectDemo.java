package com.tapestry5book.pages.chapter06;

import java.util.Arrays;
import java.util.List;

//import org.apache.tapestry5.annotations.DiscardAfter;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;

public class SelectDemo {

	@Persist
	@Property
	private String paymentType;

	@Inject
	private Logger logger;

	public List<String> getOptions() {
		return Arrays.asList("Credit Card", "Bank Transfer", "Cash", "PayPal");
	}

	// @DiscardAfter
	void onSuccess() {
		logger.info("Selected payment type {} ", paymentType);
	}
}