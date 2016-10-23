package com.tapestry5book.pages.chapter03;


import com.tapestry5book.pages.Index;
import org.apache.tapestry5.annotations.InjectPage;

public class ReturnTypesDemo {

    @InjectPage
    private Index index;

    Object onNavigateByPageClass() {
        return Index.class;
    }

    Object onNavigateByPageName() {
        return "Index";
    }

    Object onNavigateByPageInstance() {
        return index;
    }

    Object onNavigateByBoolean() {
        return true;
    }

    void onNavigateByVoid() {

    }

    Object onNavigateByNull() {
        return null;
    }
}
