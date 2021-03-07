package com.amazon.automationExercice;

import java.util.Map;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.amazon.base.CsvDataProviders;
import com.amazon.base.TestUtilities;
import com.amazon.pages.AddedToCartPageObject;
import com.amazon.pages.CartPageObject;
import com.amazon.pages.ItemPageObject;
import com.amazon.pages.ResultsPageObject;
import com.amazon.pages.WelcomePageObject;

public class AutomationExerciceWithCSV extends TestUtilities{

	@Test(invocationCount = 1, dataProvider = "csvReader", dataProviderClass = CsvDataProviders.class)
	public void scenarioUsingCsv(Map<String, String> testData) {
		// Data
		String number = testData.get("number");
		String firstItem = testData.get("firstItem");
		String quantity1 = testData.get("quantity1");
		String secondItem = testData.get("secondItem");
		String quantity2 = testData.get("quantity2");
		
		log.info("Starting test #" + number);
		Reporter.log("Starting test #" + number);

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
		manHatPage.selectQuantity(Integer.valueOf(quantity1));
		AddedToCartPageObject addedCartPage = manHatPage.clickAddToCart();

		// Open cart and assert total price and quantity are correct
		CartPageObject cartPage = addedCartPage.openCart();

		int expectedQuantity = Integer.valueOf(quantity1);
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
		womenHatPage.selectQuantity(Integer.valueOf(quantity2));
		addedCartPage = womenHatPage.clickAddToCart();

		// Open cart and assert total price and quantity are correct
		addedCartPage.openCart();

		expectedQuantity = Integer.valueOf(quantity1) + Integer.valueOf(quantity2);
		expectedTotal += womenHatPage.getHatPrice();
		actualQuantity = cartPage.getTotalQuantity();
		actualTotal = cartPage.getTotalPrice();

		expectedTotal = roundDecimalNumber(expectedTotal);
		
		Assert.assertEquals(actualQuantity, expectedQuantity, "Quantity is not correct! There are " + actualQuantity
				+ " items but there should be " + expectedQuantity + "!");
		Assert.assertEquals(actualTotal, expectedTotal,
				"Total is not correct! It is " + actualTotal + " and should be " + expectedTotal + "!");

		// Change the quantity for item selected at step 3 from 2 to 1 item in Cart
		quantity1 = String.valueOf((cartPage.changeNumberOfItem(2,1)));

		// Assert total price and quantity are changed correctly
		expectedQuantity = Integer.valueOf(quantity1) + Integer.valueOf(quantity2);
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