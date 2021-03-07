package com.amazon.base;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;

public class TestUtilities extends BaseTest {

	// Variable
	private DecimalFormat decimalFormat = new DecimalFormat("#.##");

	// Methods
	/** Allow user to stop the driver for m milliseconds. **/
	protected void sleep(long m) {
		try {
			Thread.sleep(m);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/** Clean number so it has only 2 decimals **/
	public float roundDecimalNumber(float number) {
		String newString = decimalFormat.format(number);
		newString = newString.replaceAll(",", ".");
		return Float.valueOf(newString);
	}
	
	/** Take screenshots **/
	@AfterMethod 
	protected void takeScreenshot(ITestResult testResult) throws IOException {
		if (testResult.getStatus() == ITestResult.FAILURE) {
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			
			String path = System.getProperty("user.dir") 
					+ File.separator + "test-output" 
					+ File.separator + "screenshots"
					+ File.separator + getTodaysDate() 
					+ File.separator + testSuiteName 
					+ File.separator + testName
					+ File.separator + testMethodName 
					+ File.separator + getSystemTime() 
					+ " " + ".png";
			try {
				FileUtils.copyFile(scrFile, new File(path));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}		
	}

	/** Todays date in yyyyMMdd format **/
	private static String getTodaysDate() {
		return (new SimpleDateFormat("yyyyMMdd").format(new Date()));
	}

	/** Current time in HHmmssSSS **/
	private String getSystemTime() {
		return (new SimpleDateFormat("HHmmssSSS").format(new Date()));
	}
}