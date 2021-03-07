package com.amazon.pages;

import java.util.List;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePageObject {

	// Variables
	protected WebDriver driver;
	protected Logger log;

	//Constructor
	public BasePageObject(WebDriver driver, Logger log) {
		this.driver = driver;
		this.log = log;
	}
	
	//Methods
	/** Open page with given URL **/
	protected void openUrl(String url) {		
		driver.get(url);
	}
	
	/** Find element using given locator **/
	protected WebElement find(By locator) {
		waitForVisibilityOf(locator, 3);
		return driver.findElement(locator);
	}
	
	/** Find all element using given Locator **/
	protected List<WebElement> findAll(By Locator) {
		return driver.findElements(Locator);
	}
	
	/** Click on WebElement **/
	protected void click(By locator, int seconds) {
		waitForVisibilityOf(locator, seconds);
		find(locator).click();
	}
	
	/** Type some text in WebElement **/
	protected void type(String text, By locator, int seconds) {
		waitForVisibilityOf(locator, seconds);
		find(locator).sendKeys(text);
	}
	
	/** Removes special characters from a string **/	
	protected String removeSpecialCharacters(String s) {
		String newString = s.replaceAll("[^0-9.]", "");
		return newString;
	}
	
	/** Wait for a WebElement to be visible **/
	protected void waitForVisibilityOf(By locator, Integer... timeOutInSeconds) {
		int attempts = 0;
		while (attempts < 2) {
			try {
				waitFor(ExpectedConditions.visibilityOfElementLocated(locator),
						(timeOutInSeconds.length > 0 ? timeOutInSeconds[0] : null));
				break;
			} catch (StaleElementReferenceException e) {
			}
			attempts++;
		}
	}
	
	private void waitFor(ExpectedCondition<WebElement> condition, Integer timeOutInSeconds) {
		timeOutInSeconds = timeOutInSeconds != null ? timeOutInSeconds : 30;
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		wait.until(condition);
	}
	
	/** Wait for a WebElement to be clickable **/
	protected void waitForElementToBeClickable(By locator, Integer timeOutInSeconds) {
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		wait.until(ExpectedConditions.elementToBeClickable(locator));		
	}
}