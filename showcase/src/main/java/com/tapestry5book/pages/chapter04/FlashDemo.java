package com.tapestry5book.pages.chapter04;

import java.util.Random;

import org.apache.tapestry5.PersistenceConstants;
//import org.apache.tapestry5.annotations.Meta;
import org.apache.tapestry5.annotations.PageReset;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
//@Meta("tapestry.persistence-strategy=flash")
//@Meta(SymbolConstants.PERSISTENCE_STRATEGY + "=" + PersistenceConstants.FLASH)

public class FlashDemo {
	@Property
	@Persist
	private Integer randomNumber;

	@Persist(PersistenceConstants.FLASH)
	@Property
	private String message;

	void onNextRandom() {
		randomNumber = new Random().nextInt();
		message = "Next random number successfully generated";
	}

	@PageReset
	void reset() {
		randomNumber = 0;
	}
}