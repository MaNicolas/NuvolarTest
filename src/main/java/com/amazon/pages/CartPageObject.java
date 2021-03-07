package com.amazon.pages;

import java.util.List;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

public class CartPageObject extends HeaderPageObject {

	//Variable
	private String quantityInCart;
	private String priceInCart;
	
	// Locators
	private By totalQuantityInCart = By.id("sc-subtotal-label-buybox");
	private By totalPriceInCart = By.id("sc-subtotal-amount-buybox");
	private By quantityDropdowns = By.xpath("//span[@class='a-dropdown-prompt']");
	private By deleteButton = By.xpath("(//input[@value='Delete'])[1]");

	// Constructor
	public CartPageObject(WebDriver driver, Logger log) {
		super (driver, log);
	}

	// Methods
	public int getTotalQuantity() {
		quantityInCart = find(totalQuantityInCart).getText();
		quantityInCart = removeSpecialCharacters(quantityInCart);
		log.info("There is a total of : " + quantityInCart + " items in the cart.");
		Reporter.log("There is a total of : " + quantityInCart + " items in the cart.");
		return Integer.parseInt(quantityInCart);
	}
	
	public float getTotalPrice() {
		priceInCart = find(totalPriceInCart).getText();
		priceInCart = removeSpecialCharacters(priceInCart);
		log.info("Total price in cart is : " + priceInCart + "€");
		Reporter.log("Total price in cart is : " + priceInCart + "€");
		return Float.valueOf(priceInCart);
	}
	
	public int changeNumberOfItem(int quantityToChange, int quantityToSet) {
		List<WebElement> quantityDropDowns = findAll(quantityDropdowns);
		
		//If hats for men and women are the same
		if(quantityDropDowns.size() == 1) {
			quantityDropDowns.get(0).click();
			click(By.xpath("//a[text()='" + (quantityToChange) + "']"), 2);
			log.info("Same hat for men and women have been selected");
			Reporter.log("Same hat for men and women have been selected");
		}
		
		//If hats for men and women are the different
		else {
			for (int i = 0; i < quantityDropDowns.size(); i++) {
				String s = quantityDropDowns.get(i).getText();
				int quantity = Integer.parseInt(s);
				
				if(quantity == quantityToChange){
					quantityDropDowns.get(i).click();
					click(By.xpath("//a[text()='" + quantityToSet + "']"), 2);
					log.info("Change item's quantity from " + quantityToChange + " to " + quantityToSet + ".");
					Reporter.log("Change item's quantity from " + quantityToChange + " to " + quantityToSet + ".");
				}
			}
		}		
		waitForElementToBeClickable(deleteButton, 5);
		return quantityToSet;
	}
}