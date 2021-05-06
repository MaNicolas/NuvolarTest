package com.amazon.automationExercice;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.amazon.base.TestUtilities;
import com.amazon.pages.AddedToCartPageObject;
import com.amazon.pages.CartPageObject;
import com.amazon.pages.ItemPageObject;
import com.amazon.pages.ResultsPageObject;
import com.amazon.pages.WelcomePageObject;

public class AutomationExercise extends TestUtilities {

	@Parameters({ "firstItem", "quantity1", "secondItem", "quantity2", "quantityToSet" })
	@Test(invocationCount = 1)
	public void scenarioUsingParameters(String firstItem, int quantity1, String secondItem, int quantity2,
			int quantityToSet) {
		log.info("Starting scenario!");
		Reporter.log("Starting scenario!");

		// Go to https://www.amazon.com
		WelcomePageObject welcomePage = new WelcomePageObject(driver, log);
		welcomePage.openPage();

		// Search for "hats for men"
		welcomePage.typeInSearchBar(firstItem);
		ResultsPageObject resultsPage = welcomePage.clickSearchButton();

		// Add first hat to Cart with quantity 2
		ItemPageObject manHatPage = resultsPage.clickFirstHat();
		manHatPage.checkIfSizeRequired();
		manHatPage.storeHatPrice();
		manHatPage.selectQuantity(quantity1);
		AddedToCartPageObject addedCartPage = manHatPage.clickAddToCart();

		// Open cart and assert total price and quantity are correct
		CartPageObject cartPage = addedCartPage.openCart();

		int expectedQuantity = quantity1;
		float expectedTotal = expectedQuantity * manHatPage.getHatPrice();
		int actualQuantity = cartPage.getTotalQuantity();
		float actualTotal = cartPage.getTotalPrice();

		expectedTotal = roundDecimalNumber(expectedTotal);

		Assert.assertEquals(actualQuantity, expectedQuantity, "Quantity is not correct! There are " + actualQuantity
				+ " items but there should be " + expectedQuantity + "!");
		Assert.assertEquals(actualTotal, expectedTotal,
				"Total is not correct! It is " + actualTotal + " and should be " + expectedTotal + "!");

		// Search for "hats for women"
		cartPage.typeInSearchBar(secondItem);
		resultsPage = cartPage.clickSearchButton();

		// Add first hat to Cart with quantity 1
		ItemPageObject womenHatPage = resultsPage.clickFirstHat();
		womenHatPage.checkIfSizeRequired();
		womenHatPage.storeHatPrice();
		womenHatPage.selectQuantity(quantity2);
		addedCartPage = womenHatPage.clickAddToCart();

		// Open cart and assert total price and quantity are correct
		addedCartPage.openCart();

		expectedQuantity = quantity1 + quantity2;
		expectedTotal += womenHatPage.getHatPrice();
		actualQuantity = cartPage.getTotalQuantity();
		actualTotal = cartPage.getTotalPrice();

		expectedTotal = roundDecimalNumber(expectedTotal);

		Assert.assertEquals(actualQuantity, expectedQuantity, "Quantity is not correct! There are " + actualQuantity
				+ " items but there should be " + expectedQuantity + "!");
		Assert.assertEquals(actualTotal, expectedTotal,
				"Total is not correct! It is " + actualTotal + " and should be " + expectedTotal + "!");

		// Change the quantity for item selected at step 3 from 2 to 1 item in Cart
		quantity1 = cartPage.changeNumberOfItem(quantity1, quantityToSet);

		// Assert total price and quantity are changed correctly
		expectedQuantity = quantity1 + quantity2;
		expectedTotal -= manHatPage.getHatPrice();
		actualQuantity = cartPage.getTotalQuantity();
		actualTotal = cartPage.getTotalPrice();

		expectedTotal = roundDecimalNumber(expectedTotal);

		Assert.assertEquals(actualQuantity, expectedQuantity, "Quantity is not correct! There are " + actualQuantity
				+ " items but there should be " + expectedQuantity + "!");
		Assert.assertEquals(actualTotal, expectedTotal,
				"Total is not correct! It is " + actualTotal + " and should be " + expectedTotal + "!");
	}
}