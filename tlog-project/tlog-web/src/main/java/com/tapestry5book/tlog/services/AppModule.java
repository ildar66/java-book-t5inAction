package com.tapestry5book.tlog.services;

import com.tapestry5book.tlog.core.entities.Blog;
import com.tapestry5book.tlog.core.entities.Skin;
import com.tapestry5book.tlog.core.services.*;
import com.tapestry5book.tlog.pages.SidebarBlocks;
import com.tapestry5book.tlog.services.impl.*;
import com.tapestry5book.tlog.skins.SkinConstants;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.Resource;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.*;
import org.apache.tapestry5.services.ApplicationStateContribution;
import org.apache.tapestry5.services.ApplicationStateCreator;
import org.apache.tapestry5.services.ApplicationStateManager;
import org.apache.tapestry5.services.ComponentRequestHandler;
import org.apache.tapestry5.services.linktransform.PageRenderLinkTransformer;
import org.slf4j.Logger;

@SubModule(CoreModule.class)
public class AppModule {

	public static void bind(final ServiceBinder binder) {
		binder.bind(Authenticator.class, AuthenticatorImpl.class);
		binder.bind(GravatarService.class, GravatarServiceImpl.class);
		binder.bind(BlogService.class, BlogServiceImpl.class);
		binder.bind(PluginPageManager.class, PluginPageManagerImpl.class);
	}

	@Contribute(ApplicationStateManager.class)
	public static void provideApplicationStateContributions(
			final MappedConfiguration<Class, ApplicationStateContribution> configuration, final BlogService blogService) {

		final ApplicationStateCreator<Blog> creator = new ApplicationStateCreator<Blog>() {
			public Blog create() {
				return blogService.findBlog();
			}

		};
		configuration.add(Blog.class, new ApplicationStateContribution(PersistenceConstants.SESSION, creator));
	}

	@Contribute(SidebarBlockSource.class)
	public static void provideSideBlocks(final OrderedConfiguration<SidebarBlockContribution> configuration) {

		configuration.add("Pages", new SidebarBlockContribution(SidebarBlocks.class, "pages"), "before:*");

		configuration.add("Blogroll", new SidebarBlockContribution(SidebarBlocks.class, "blogroll"), "after:Pages");

		configuration.add("Meta", new SidebarBlockContribution(SidebarBlocks.class, "meta"), "after:*");
	}

	@Contribute(ComponentRequestHandler.class)
	public static void provideComponentRequestFilters(OrderedConfiguration configuration) {
		configuration.addInstance("PageAccess", PageAccessFilter.class);
	}

	@Contribute(SkinManager.class)
	public static void provideSkins(MappedConfiguration<Skin, SkinResources> configuration,
			@Value("/com/tapestry5book/tlog/skins/default.xml") Resource defaultSkin,
			@Value("/com/tapestry5book/tlog/skins/default.png") Resource defaultSkinPreview) {
		configuration.add(SkinConstants.DEFAULT_SKIN, new SkinResources(defaultSkinPreview, defaultSkin));
	}

	@Contribute(PageRenderLinkTransformer.class)
	@Primary
	public static void provideURLRewriting(OrderedConfiguration<PageRenderLinkTransformer> configuration) {

		configuration.addInstance("PluginPageLinkTransformer", PluginPageLinkTransformer.class);
	}

	public static DemoDataParser buildDemoDataParser(Logger logger) {
		return new DemoDataParser(logger);
	}

	@Startup
	public static void initDemoData(@Autobuild final DemoDataSource source) {
		source.create();
	}

	public static void contributeApplicationDefaults(MappedConfiguration<String, String> configuration) {

		// The factory default is true but during the early stages of an
		// application
		// overriding to false is a good idea. In addition, this is often
		// overridden
		// on the command line as -Dtapestry.production-mode=false
		configuration.add(SymbolConstants.PRODUCTION_MODE, "false");
	}

}
