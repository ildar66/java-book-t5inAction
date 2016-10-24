package com.tapestry5book.pages.chapter04;

import java.util.Random;

//import javax.inject.Inject;

//import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.DiscardAfter;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;

public class DiscardDemo {
//	@Inject
//	private ComponentResources componentResources;

    @Property
    @Persist
    private Integer randomNumber;

    void onNextRandom() {
        randomNumber = new Random().nextInt();
    }

    @DiscardAfter
    void onClear() {
//    	componentResources.discardPersistentFieldChanges();
    }
}