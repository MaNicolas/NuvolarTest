package com.amazon.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import org.apache.logging.log4j.Logger;

public class HeaderPageObject extends BasePageObject{

	//Locators
	private By searchBar = By.id("twotabsearchtextbox");
	private By searchButton = By.id("nav-search-submit-button");
	
	//Constructor
	public HeaderPageObject(WebDriver driver, Logger log) {
		super(driver, log);
	}
	
	//Methods
	public void typeInSearchBar(String text) {
		type(text, searchBar, 5);
		log.info("Searching for item: '" + text + "'.");
		Reporter.log("Searching for item: '" + text + "'.");
	}
	
	public ResultsPageObject clickSearchButton() {
		click(searchButton, 5);
		return new ResultsPageObject (driver, log);
	}
}