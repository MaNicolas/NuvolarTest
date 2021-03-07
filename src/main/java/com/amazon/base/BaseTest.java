package com.amazon.base;

import java.lang.reflect.Method;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

@Listeners({com.amazon.base.TestListener.class})
public class BaseTest {

	// Variables
	protected WebDriver driver;
	protected Logger log;
	
	protected String testSuiteName;
	protected String testName;
	protected String testMethodName;

	//Methods
	@Parameters({ "browser" })
	@BeforeMethod(alwaysRun = true)
	public void setup(Method method, @Optional("chrome") String browser, ITestContext ctx) {
		String testName = ctx.getCurrentXmlTest().getName();
		log = LogManager.getLogger(testName);
		
		BrowserDriverFactory factory = new BrowserDriverFactory(browser, log);
		driver = factory.createDriver();
		driver.manage().window().maximize();
		
		this.testSuiteName = ctx.getSuite().getName();
		this.testName = testName;
		this.testMethodName = method.getName();
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		// Close browser
		log.info("Closing browser");
		//driver.quit();
	}
}