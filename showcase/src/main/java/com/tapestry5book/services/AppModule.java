package com.tapestry5book.services;

import java.io.IOException;

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.hibernate.HibernateTransactionAdvisor;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.MethodAdviceReceiver;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Advise;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.Local;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.RequestFilter;
import org.apache.tapestry5.services.RequestHandler;
import org.apache.tapestry5.services.Response;
import org.apache.tapestry5.services.ValueEncoderFactory;
import org.apache.tapestry5.services.ValueEncoderSource;
import org.slf4j.Logger;

import com.tapestry5book.entities.Article;
import com.tapestry5book.services.impl.ArticleEncoder;
import com.tapestry5book.services.impl.BlogServiceImpl;

public class AppModule {
	public static void bind(ServiceBinder binder) {
		// binder.bind(MyServiceInterface.class, MyServiceImpl.class);
		binder.bind(BlogService.class, BlogServiceImpl.class);

	}

	public static void contributeApplicationDefaults(
			MappedConfiguration<String, String> configuration) {

		// The factory default is true but during the early stages of an
		// application
		// overriding to false is a good idea. In addition, this is often
		// overridden
		// on the command line as -Dtapestry.production-mode=false
		configuration.add(SymbolConstants.PRODUCTION_MODE, "false");
	}

	/**
	 * This is a service definition, the service will be named "TimingFilter".
	 * The interface, RequestFilter, is used within the RequestHandler service
	 * pipeline, which is built from the RequestHandler service configuration.
	 * Tapestry IoC is responsible for passing in an appropriate Logger
	 * instance. Requests for static resources are handled at a higher level, so
	 * this filter will only be invoked for Tapestry related requests.
	 * <p/>
	 * <p/>
	 * Service builder methods are useful when the implementation is inline as
	 * an inner class (as here) or require some other kind of special
	 * initialization. In most cases, use the static bind() method instead.
	 * <p/>
	 * <p/>
	 * If this method was named "build", then the service id would be taken from
	 * the service interface and would be "RequestFilter". Since Tapestry
	 * already defines a service named "RequestFilter" we use an explicit
	 * service id that we can reference inside the contribution method.
	 */
	public RequestFilter buildTimingFilter(final Logger log) {
		return new RequestFilter() {
			public boolean service(Request request, Response response,
					RequestHandler handler) throws IOException {
				long startTime = System.currentTimeMillis();

				try {
					// The responsibility of a filter is to invoke the
					// corresponding method
					// in the handler. When you chain multiple filters together,
					// each filter
					// received a handler that is a bridge to the next filter.

					return handler.service(request, response);
				} finally {
					long elapsed = System.currentTimeMillis() - startTime;

					log.info(String.format("Request time: %d ms", elapsed));
				}
			}
		};
	}

	/**
	 * This is a contribution to the RequestHandler service configuration. This
	 * is how we extend Tapestry using the timing filter. A common use for this
	 * kind of filter is transaction management or security. The
	 * 
	 * @Local annotation selects the desired service by type, but only from the
	 *        same module. Without
	 * @Local, there would be an error due to the other service(s) that
	 *         implement RequestFilter (defined in other modules).
	 */
	public void contributeRequestHandler(
			OrderedConfiguration<RequestFilter> configuration,
			@Local RequestFilter filter) {
		// Each contribution to an ordered configuration has a name, When
		// necessary, you may
		// set constraints to precisely control the invocation order of the
		// contributed filter
		// within the pipeline.

		configuration.add("Timing", filter);
	}

	@Contribute(ValueEncoderSource.class)
	public static void provideEncoders(
			MappedConfiguration<Class, ValueEncoderFactory> configuration,
			final BlogService blogService) {
		/*
		 * ValueEncoderFactory<Article> factory = new
		 * ValueEncoderFactory<Article>() { public ValueEncoder<Article>
		 * create(Class<Article> clazz) { return new
		 * ArticleEncoder(blogService); } }; configuration.add(Article.class,
		 * factory);
		 */
		contributeEncoder(configuration, Article.class, new ArticleEncoder(
				blogService));
	}

	private static <T> void contributeEncoder(
			MappedConfiguration<Class, ValueEncoderFactory> configuration,
			Class<T> clazz, final ValueEncoder<T> encoder) {

		ValueEncoderFactory<T> factory = new ValueEncoderFactory<T>() {

			public ValueEncoder<T> create(Class<T> clazz) {
				return encoder;
			}
		};

		configuration.add(clazz, factory);
	}

	@Advise(serviceInterface = BlogService.class)
	public static void adviseTransactionally(
			HibernateTransactionAdvisor advisor, MethodAdviceReceiver receiver) {
		advisor.addTransactionCommitAdvice(receiver);
	}

}
