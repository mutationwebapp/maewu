package com.crawljax.examples;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.slf4j.LoggerFactory;

import com.crawljax.browser.EmbeddedBrowser.BrowserType;
import com.crawljax.core.CrawljaxRunner;
import com.crawljax.core.configuration.BrowserConfiguration;
import com.crawljax.core.configuration.CrawlRules.FormFillMode;
import com.crawljax.core.configuration.CrawljaxConfiguration;
import com.crawljax.core.configuration.CrawljaxConfiguration.CrawljaxConfigurationBuilder;
import com.crawljax.plugins.crawloverview.CrawlOverview;
import com.crawljax.stateabstractions.dom.RTEDStateVertexFactory;
import com.crawljax.stateabstractions.hybrid.HybridStateVertexFactory;

import ch.qos.logback.classic.Logger;

/**
 * Example of running Crawljax with the CrawlOverview plugin on a single-page web app. The crawl
 * will produce output using the {@link CrawlOverview} plugin. Default output dir is "out".
 */
public final class CommandLineExample {

	private static final long WAIT_TIME_AFTER_EVENT = 500;
	private static final long WAIT_TIME_AFTER_RELOAD = 500;
	private static  String URL = "http://localhost:9966/petclinic/";

	/**
	 * Run this method to start the crawl.
	 * 
	 * @throws IOException
	 *             when the output folder cannot be created or emptied.
	 */
	public static void main(String[] args) throws IOException {
		URL = args[0];
		
		long runtime = Long.parseLong(args[1]);
		CrawljaxConfigurationBuilder builder = CrawljaxConfiguration.builderFor(URL);
		
		builder.setStateVertexFactory(new HybridStateVertexFactory(0, builder, false));
		builder.crawlRules().setFormFillMode(FormFillMode.RANDOM);
		builder.setMaximumRunTime(runtime, TimeUnit.MINUTES);
		// click these elements
		builder.crawlRules().clickDefaultElements();
		 /*builder.crawlRules().click("A");
		 builder.crawlRules().click("button");*/
		builder.crawlRules().crawlHiddenAnchors(true);
		builder.crawlRules().crawlFrames(false);
		builder.setUnlimitedCrawlDepth();
		//builder.setUnlimitedRuntime();
		builder.setUnlimitedStates();

		//builder.setMaximumStates(10);
		//builder.setMaximumDepth(3);
		builder.crawlRules().clickElementsInRandomOrder(false);

		// Set timeouts
		builder.crawlRules().waitAfterReloadUrl(WAIT_TIME_AFTER_RELOAD, TimeUnit.MILLISECONDS);
		builder.crawlRules().waitAfterEvent(WAIT_TIME_AFTER_EVENT, TimeUnit.MILLISECONDS);

		builder.setBrowserConfig(new BrowserConfiguration(BrowserType.CHROME, 1));

		// CrawlOverview
		builder.addPlugin(new CrawlOverview());

		CrawljaxRunner crawljax = new CrawljaxRunner(builder.build());
		crawljax.call();

	}

}
