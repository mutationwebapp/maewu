package com.crawljax.examples.ppma;

import java.util.concurrent.TimeUnit;

import com.crawljax.browser.EmbeddedBrowser.BrowserType;
import com.crawljax.core.configuration.BrowserConfiguration;
import com.crawljax.core.configuration.CrawljaxConfiguration.CrawljaxConfigurationBuilder;
import com.crawljax.core.state.Eventable.EventType;
import com.crawljax.core.configuration.InputSpecification;
import com.crawljax.examples.Plugin;
import com.crawljax.fragmentation.FragmentRules;
import com.crawljax.core.configuration.CrawlRules.CrawlPriorityMode;
import com.crawljax.core.configuration.CrawlRules.FormFillMode;
import com.crawljax.plugins.crawloverview.CrawlOverview;

public class PpmaCrawlingRules {
	
	//			Useful fragment 
	//if ((fragment.getRect().getWidth() > 100 && fragment.getRect().getHeight() > 100 && subtreeWidth>=4) || subtreeWidth >= 4) {

	
	/**
	 * List of crawling rules for the AddressBook application.
	 */
	public static void setCrawlingRules(CrawljaxConfigurationBuilder builder) {

		/* crawling rules. */
		builder.crawlRules().clickElementsInRandomOrder(false);
		builder.crawlRules().clickDefaultElements();
		builder.crawlRules().crawlHiddenAnchors(true);
		builder.crawlRules().crawlFrames(false);
		builder.crawlRules().clickOnce(false);
		builder.crawlRules().avoidUnrelatedBacktracking(true);
		builder.crawlRules().applyNonSelAdvantage(true);
//		builder.crawlRules().addEventType(EventType.hover);

		builder.crawlRules().setFormFillMode(FormFillMode.RANDOM);
		builder.crawlRules().avoidDifferentBacktracking(true);
		
		builder.crawlRules().skipExploredActions(false, 3);
		
		builder.crawlRules().setUsefulFragmentRules(new FragmentRules(100, 100, 2, 4));
//		builder.crawlRules().click("a", "button", "input");

		/* do not click these. */
		builder.crawlRules().dontClick("a").withText("Export to CSV");
		builder.crawlRules().dontClick("input").withAttribute("id", "upload-file");
//		builder.crawlRules().dontClick("a").withText("Tags");
//		builder.crawlRules().dontClick("a").withText("Settings");
//		builder.crawlRules().dontClick("a").withText("Profile");
		
		//builder.crawlRules().setCrawlPriorityMode(CrawlPriorityMode.OLDEST_FIRST);

		/* set timeouts. */
		builder.setUnlimitedCrawlDepth();
		// builder.setMaximumRunTime(30, TimeUnit.MINUTES);
		builder.setUnlimitedStates();
		//builder.setMaximumStates(150);
		//builder.setUnlimitedRuntime();
		builder.setMaximumRunTime(5, TimeUnit.MINUTES);
		builder.crawlRules().waitAfterReloadUrl(PpmaRunner.WAIT_TIME_AFTER_RELOAD, TimeUnit.MILLISECONDS);
		builder.crawlRules().waitAfterEvent(PpmaRunner.WAIT_TIME_AFTER_EVENT, TimeUnit.MILLISECONDS);

		/* set browser. */
		builder.setBrowserConfig(new BrowserConfiguration(BrowserType.CHROME, 1));

		/* input data. */
		builder.crawlRules().setInputSpec(PpmaCrawlingRules.collabtiveInputSpecification());

		/* CrawlOverview. */
		builder.addPlugin(new CrawlOverview());
	}

	/**
	 * List of inputs to crawl the AddressBook application.
	 */
	static InputSpecification collabtiveInputSpecification() {

		InputSpecification ppmaInput= new InputSpecification();

		PpmaForms.login(ppmaInput);
		PpmaForms.newEntry(ppmaInput);
//		CollabtiveForms.newEntry(inputAddressBook);
//		CollabtiveForms.newUser(inputAddressBook);

		return ppmaInput;
	}

}