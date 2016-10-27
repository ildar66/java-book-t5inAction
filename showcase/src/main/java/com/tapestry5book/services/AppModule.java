package com.tapestry5book.services;

import java.io.IOException;
import java.util.Currency;

import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.Translator;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.hibernate.HibernateTransactionAdvisor;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.MethodAdviceReceiver;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Advise;
import org.apache.tapestry5.ioc.annotations.Autobuild;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.Local;
import org.apache.tapestry5.ioc.annotations.Startup;
import org.apache.tapestry5.services.ApplicationStateContribution;
import org.apache.tapestry5.services.ApplicationStateCreator;
import org.apache.tapestry5.services.ApplicationStateManager;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.RequestFilter;
import org.apache.tapestry5.services.RequestHandler;
import org.apache.tapestry5.services.Response;
import org.apache.tapestry5.services.TranslatorSource;
import org.apache.tapestry5.services.ValueEncoderFactory;
import org.apache.tapestry5.services.ValueEncoderSource;
import org.apache.tapestry5.validator.ValidatorMacro;
import org.slf4j.Logger;

import com.tapestry5book.entities.Blog;
import com.tapestry5book.entities.ShoppingCart;
import com.tapestry5book.entities.Track;
import com.tapestry5book.services.impl.AuthenticatorImpl;
import com.tapestry5book.services.impl.BlogServiceImpl;
import com.tapestry5book.services.impl.CurrencyTranslator;
import com.tapestry5book.services.impl.DemoDataParser;
import com.tapestry5book.services.impl.DemoDataSource;
import com.tapestry5book.services.impl.MusicLibraryImpl;
import com.tapestry5book.services.impl.PasswordPolicyServiceImpl;
import com.tapestry5book.services.impl.ReportServiceImpl;
import com.tapestry5book.services.impl.TrackEncoder;
import com.tapestry5book.services.impl.TrackPriceServiceImpl;
import com.tapestry5book.services.impl.UserDaoImpl;
import com.tapestry5book.services.impl.VoteServiceImpl;

public class AppModule {
	public static void bind(ServiceBinder binder) {
		// binder.bind(MyServiceInterface.class, MyServiceImpl.class);
		binder.bind(Authenticator.class, AuthenticatorImpl.class);
		binder.bind(PasswordPolicyService.class, PasswordPolicyServiceImpl.class);
		binder.bind(UserDao.class, UserDaoImpl.class);

		binder.bind(BlogService.class, BlogServiceImpl.class);
		binder.bind(TrackPriceService.class, TrackPriceServiceImpl.class);

		binder.bind(ReportService.class, ReportServiceImpl.class);
		binder.bind(VoteService.class, VoteServiceImpl.class);
	}

	public static void contributeApplicationDefaults(MappedConfiguration<String, String> configuration) {

		// The factory default is true but during the early stages of an
		// application
		// overriding to false is a good idea. In addition, this is often
		// overridden
		// on the command line as -Dtapestry.production-mode=false
		configuration.add(SymbolConstants.PRODUCTION_MODE, "false");
		configuration.add(SymbolConstants.SUPPORTED_LOCALES, "en,de,ru,iw");

		// Configuration of file uploads
		// configuration.add(UploadSymbols.REPOSITORY_THRESHOLD, "5120");
		// configuration.add(UploadSymbols.REPOSITORY_LOCATION, System.getProperty("java.io.tmpdir"));
		// configuration.add(UploadSymbols.REQUESTSIZE_MAX, "-1");
		// configuration.add(UploadSymbols.FILESIZE_MAX, "1048576");
	}

	/**
	 * This is a service definition, the service will be named "TimingFilter". The interface, RequestFilter, is used
	 * within the RequestHandler service pipeline, which is built from the RequestHandler service configuration.
	 * Tapestry IoC is responsible for passing in an appropriate Logger instance. Requests for static resources are
	 * handled at a higher level, so this filter will only be invoked for Tapestry related requests.
	 * <p/>
	 * <p/>
	 * Service builder methods are useful when the implementation is inline as an inner class (as here) or require some
	 * other kind of special initialization. In most cases, use the static bind() method instead.
	 * <p/>
	 * <p/>
	 * If this method was named "build", then the service id would be taken from the service interface and would be
	 * "RequestFilter". Since Tapestry already defines a service named "RequestFilter" we use an explicit service id
	 * that we can reference inside the contribution method.
	 */
	public RequestFilter buildTimingFilter(final Logger log) {
		return new RequestFilter() {
			public boolean service(Request request, Response response, RequestHandler handler) throws IOException {
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
	 * This is a contribution to the RequestHandler service configuration. This is how we extend Tapestry using the
	 * timing filter. A common use for this kind of filter is transaction management or security. The
	 * 
	 * @Local annotation selects the desired service by type, but only from the same module. Without
	 * @Local, there would be an error due to the other service(s) that implement RequestFilter (defined in other
	 *         modules).
	 */
	public void contributeRequestHandler(OrderedConfiguration<RequestFilter> configuration, @Local RequestFilter filter) {
		// Each contribution to an ordered configuration has a name, When
		// necessary, you may
		// set constraints to precisely control the invocation order of the
		// contributed filter
		// within the pipeline.

		configuration.add("Timing", filter);
	}

	@Contribute(ValueEncoderSource.class)
	public static void provideEncoders(MappedConfiguration<Class, ValueEncoderFactory> configuration,
			final BlogService blogService, MusicLibrary musicLibrary) {
		/*
		 * ValueEncoderFactory<Article> factory = new ValueEncoderFactory<Article>() { public ValueEncoder<Article>
		 * create(Class<Article> clazz) { return new ArticleEncoder(blogService); } }; configuration.add(Article.class,
		 * factory);
		 */
		// contributeEncoder(configuration, Article.class, new
		// ArticleEncoder(blogService));
		contributeEncoder(configuration, Track.class, new TrackEncoder(musicLibrary));
	}

	private static <T> void contributeEncoder(MappedConfiguration<Class, ValueEncoderFactory> configuration,
			Class<T> clazz, final ValueEncoder<T> encoder) {

		ValueEncoderFactory<T> factory = new ValueEncoderFactory<T>() {

			public ValueEncoder<T> create(Class<T> clazz) {
				return encoder;
			}
		};

		configuration.add(clazz, factory);
	}

	@Advise(serviceInterface = BlogService.class)
	public static void adviseTransactionally(HibernateTransactionAdvisor advisor, MethodAdviceReceiver receiver) {
		advisor.addTransactionCommitAdvice(receiver);
	}

	@Contribute(ApplicationStateManager.class)
	public static void provideApplicationStateContributions(
			final MappedConfiguration<Class, ApplicationStateContribution> configuration,
			final BlogService blogService, final TrackPriceService trackPriceService) {

		final ApplicationStateCreator<Blog> blogCreator = new ApplicationStateCreator<Blog>() {
			public Blog create() {
				return blogService.findBlog();
			}

		};

		ApplicationStateCreator<ShoppingCart> shoppingCartCreator = new ApplicationStateCreator<ShoppingCart>() {
			public ShoppingCart create() {
				return new ShoppingCart(trackPriceService);
			}
		};

		configuration.add(Blog.class, new ApplicationStateContribution(PersistenceConstants.SESSION, blogCreator));

		configuration.add(ShoppingCart.class, new ApplicationStateContribution(PersistenceConstants.SESSION,
				shoppingCartCreator));
	}

	public MusicLibrary buildMusicLibrary(Logger logger) {
		return new MusicLibraryImpl(logger);
	}

	public static DemoDataParser buildDemoDataParser(Logger logger) {
		return new DemoDataParser(logger);
	}

	@Startup
	public static void initDemoData(@Autobuild final DemoDataSource source) {
		source.create();
	}

	@Contribute(ValidatorMacro.class)
	public static void combineValidators(MappedConfiguration<String, String> configuration) {
		configuration.add("requiredMinMax", "required,minlength=3,maxlength=50");
	}

	@Contribute(TranslatorSource.class)
	public static void provideTranslators(MappedConfiguration<Class, Translator> configuration) {
		configuration.add(Currency.class, new CurrencyTranslator());
	}

}
