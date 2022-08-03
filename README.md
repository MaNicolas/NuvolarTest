# NuvolarTest

Hi,

Here is a small documentation regarding the setup required and how to launch the tests.

In order to run the test, you'll need the following setup:
- Java version 1.8.0
- jdk-11.0.6
- Chrome browser version 88.0.4324.182
- Firefox browser version 86.0 (64-bit)
- Eclipse IDE

I am using a Maven project structure with TestNG plugin to run my tests.
The design pattern is Page Object Model.
You will find 3 test suites XML files under "src\test\resources\TestSuites":
- AutomationExerciseTestSuite.xml (which basically run my test on Chrome and Firefox in parallel using parameters)
- WithCsvTestSuite.xml (The same but using a CSV file present in "src\test\resources\dataproviders")
- AllTests.xml (Which is running the 2 XML files)

To run the tests:
- Right click on one of those files
- "Run as"
- TestNG Suite

As for the report, you can find it in the console or under "NuvolarTest\test-output\emailable-report.html",
"NuvolarTest\test-output\index.html" or finally "NuvolarTest\test-output\testng-results.xml"

Please note that throughout my code, you will find those 2 lines:
log.info("This line is for the console");
Reporter.log("This one is for the report in html");

I usually only use the "log.info", but I left both of them so you can see the logs wherever you want.

Finally, if you wish to run the test many times automatically, I left the "@Test(invocationCount = X)" parameter available above the test method's name.
Just edit this parameter to your liking.

Nicolas
