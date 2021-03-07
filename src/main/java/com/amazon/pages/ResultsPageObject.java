package com.amazon.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.apache.logging.log4j.Logger;

public class ResultsPageObject extends HeaderPageObject {

	// Locators
	private By firstArticle = By.xpath("(//span[@class='a-price'])[1]");

	// Constructor
	public ResultsPageObject(WebDriver driver, Logger log) {
		super(driver, log);
	}

	// Methods
	/** Click on first article with a price **/
	public ItemPageObject clickFirstHat() {
		click(firstArticle, 5);
		return new ItemPageObject(driver, log);
	}
}