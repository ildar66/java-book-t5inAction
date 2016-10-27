package com.tapestry5book.pages.chapter06;

import java.util.Currency;

import org.apache.tapestry5.ValidationException;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.beaneditor.Translate;

public class TranslatorDemo {

	@Property
	@Persist
	private Currency currency;

	@Property
	@Persist
	@Translate("integer")
	private Number age;

	/**
	 * Any translator registered in the framework is global; it is applied to all form fields which match the type
	 * associated with the translator. Sometimes you will need to override a translator in a page locally. This can be
	 * accomplished by implementing handler methods for parseClient and toClient events, which are fired by the
	 * TextField, TextArea and PasswordField components
	
		public Currency onParseClientFromCurrencyField(String value) throws ValidationException {
			return Currency.getInstance(value);
		}
	
		public String onToClientFromCurrencyField(Currency currency) {
			if (currency != null) {
				return currency.toString();
			}
			return null;
		}
	 */
}