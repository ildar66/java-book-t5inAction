package com.tapestry5book.tlog.core.services;

import com.tapestry5book.tlog.core.entities.Month;
import com.tapestry5book.tlog.core.entities.Tag;
import com.tapestry5book.tlog.core.services.impl.*;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.hibernate.HibernateEntityPackageManager;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.services.ValueEncoderFactory;
import org.apache.tapestry5.services.ValueEncoderSource;

public class CoreModule {

	public static void bind(final ServiceBinder binder) {
		binder.bind(SidebarBlockSource.class, SidebarBlockSourceImpl.class);
		binder.bind(SkinManager.class, SkinManagerImpl.class);
		binder.bind(StartPageLinkSource.class, StartPageLinkSourceImpl.class);
	}

	@Contribute(HibernateEntityPackageManager.class)
	public static void provideHibernateEntityPackages(Configuration<String> configuration) {
		configuration.add("com.tapestry5book.tlog.core.entities");
	}

	@Contribute(ValueEncoderSource.class)
	public static void contributeValueEncoders(MappedConfiguration<Class, ValueEncoderFactory> configuration) {

		configuration.add(Month.class, new ValueEncoderFactory<Month>() {

			public ValueEncoder<Month> create(Class<Month> type) {
				return new MonthValueEncoder();
			}
		});

		configuration.override(Tag.class, new ValueEncoderFactory<Tag>() {

			public ValueEncoder<Tag> create(Class<Tag> type) {
				return new TagValueEncoder();
			}
		});
	}
}
