package com.amazon.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.apache.logging.log4j.Logger;

public class AddedToCartPageObject extends HeaderPageObject {	

	// Locators
	private By openCart = By.id("hlb-view-cart-announce");
	
	// Constructor
	public AddedToCartPageObject(WebDriver driver, Logger log) {
		super(driver, log);
	}

	// Methods
	public CartPageObject openCart() {
		click(openCart, 5);
		return new CartPageObject(driver, log);
	}
}