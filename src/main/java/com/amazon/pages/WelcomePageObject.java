package com.amazon.pages;

import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import org.apache.logging.log4j.Logger;

public class WelcomePageObject extends HeaderPageObject {

	// Variables
	private String pageUrl = "https://www.amazon.com/";

	//Constructor
	public WelcomePageObject(WebDriver driver, Logger log) {
		super(driver, log);
	}

	// Methods
	public void openPage() {
		log.info("Opening page: " + pageUrl);
		Reporter.log("Opening page: " + pageUrl);
		openUrl(pageUrl);
		log.info("Page opened!");
		Reporter.log("Page opened!");
	}
}