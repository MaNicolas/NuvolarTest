package com.amazon.pages;

import java.util.List;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

public class ItemPageObject extends HeaderPageObject {

	//Variables
	private float hatPrice;
	
	// Locatorsprivate
	private By sizeDropDown = By.xpath("//span[@id='dropdown_selected_size_name']");
	private By availableSizeDropDown = By.xpath("//li[@class='a-dropdown-item dropdownAvailable']");
	private By hatPriceLocator = By.id("price_inside_buybox");
	private By quantityDropdown = By.xpath("//span[@class='a-button a-button-dropdown a-button-small']");
	private By addToCartButton = By.id("add-to-cart-button");
	
	// Constructor
	public ItemPageObject(WebDriver driver, Logger log) {
		super(driver, log);
	}

	// Methods
	public void checkIfSizeRequired() {
		try {
			click(sizeDropDown, 2);
			log.info("User needs to select a size!");
			Reporter.log("User needs to select a size!");
			
			List<WebElement> sizeDropDowns = findAll(availableSizeDropDown);
			for (int i = 0; i < sizeDropDowns.size(); i++) {
				sizeDropDowns.get(i+1).click();
				
				try {
					storeHatPrice();
					log.info("Size is available AND sold by amazon!");
					Reporter.log("Size is available AND sold by amazon!");
					break;
				} catch (Exception e) {
					log.info("Size is not available or not sold by amazon!");
					Reporter.log("Size is not available or not sold by amazon!");
					click(sizeDropDown, 2);
				}
			}
		} catch (Exception e) {
			Reporter.log("Hat has only one size!");
		}
	}
	
	public void storeHatPrice() {
		String text = find(hatPriceLocator).getText();
		text = removeSpecialCharacters(text);
		hatPrice = Float.valueOf(text);
		log.info("Hat's price is: " + hatPrice + "€");
		Reporter.log("Hat's price is: " + hatPrice + "€");
	}
	
	public float getHatPrice() {
		return hatPrice;
	}
	
	public void selectQuantity(int quantity) {
		try {
			click(quantityDropdown, 2);
			click(By.id("quantity_"+ (quantity-1)), 2);
		} catch (Exception e) {
			log.info("Item is not available yet! Ordering it now!");
			Reporter.log("Item is not available yet! Ordering it now!");
		}		
	}
	
	public AddedToCartPageObject clickAddToCart() {
		click(addToCartButton, 5);
		log.info("Add item(s) to cart!");
		Reporter.log("Add item(s) to cart!");
		return new AddedToCartPageObject(driver, log);
	}
}