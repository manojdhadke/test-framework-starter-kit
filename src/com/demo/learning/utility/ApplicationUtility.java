package com.demo.learning.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.function.Function;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

import io.codearte.jfairy.Fairy;
import io.codearte.jfairy.producer.company.Company;
import io.codearte.jfairy.producer.person.Person;
import io.codearte.jfairy.producer.text.TextProducer;

public class ApplicationUtility {

	Properties propertiesObject;
	FileInputStream propertiesFile;
	Fairy fairy;
	Person person;

	public static ExtentHtmlReporter htmlReporter;
	public static ExtentReports extentReports;
	public static ExtentTest extentTest;

	@BeforeSuite
	public void generate() {
		htmlReporter = new ExtentHtmlReporter("./report/Automation_Test_Report.html");
		extentReports = new ExtentReports();
		extentReports.attachReporter(htmlReporter);
		htmlReporter.config().setChartVisibilityOnOpen(true);
		htmlReporter.config().setDocumentTitle("Automation Test Scripts Report");
		htmlReporter.config().setReportName("Test Cases Report");
		htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
		htmlReporter.config().setTheme(Theme.DARK);
	}

	public void getResult(ITestResult testResult, String screenshotDestination) throws IOException {
		if (testResult.getStatus() == ITestResult.FAILURE) {
			extentTest.log(Status.FAIL, MarkupHelper
					.createLabel(testResult.getName() + " Test case FAILED due to below issues:", ExtentColor.RED));
			extentTest.fail(testResult.getThrowable());
			extentTest.addScreenCaptureFromPath("." + screenshotDestination);

		} else if (testResult.getStatus() == ITestResult.SUCCESS) {
			extentTest.log(Status.PASS,
					MarkupHelper.createLabel(testResult.getName() + " Test Case PASSED", ExtentColor.GREEN));
			extentTest.addScreenCaptureFromPath("." + screenshotDestination);

		} else {
			extentTest.log(Status.SKIP,
					MarkupHelper.createLabel(testResult.getName() + " Test Case SKIPPED", ExtentColor.ORANGE));
			extentTest.skip(testResult.getThrowable());

		}
	}

	@AfterSuite
	public void tearDown() {
		extentReports.flush();
	}

	public WebDriver init(String url) {
		WebDriver driver;
		/*
		 * To use firefox browser System.setProperty("webdriver.gecko.driver",
		 * "./driver/geckodriver");
		 */
		System.setProperty("webdriver.chrome.driver", "./driver/chromedriver");
		/*
		 * ChromeOptions options = new ChromeOptions();
		 * options.addArguments("headless");
		 * options.addArguments("window-size=1200x600"); driver = new
		 * ChromeDriver(options);
		 */

		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get(url);
		waitPageLoad(driver);
		return driver;
	}

	public String getPropertyFileData(String data){
		propertiesObject = new Properties();
		String valueFromPropertiesFile;
		try {
			propertiesFile = new FileInputStream(System.getProperty("user.dir") + "/testsettings.properties/");
			propertiesObject.load(propertiesFile);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.getMessage();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}
		valueFromPropertiesFile = propertiesObject.getProperty(data);
		return valueFromPropertiesFile;
	}

	public TextProducer getRawData() {
		TextProducer textProducer = fairy.textProducer();
		return textProducer;
	}

	public String getTestData(String testData) {
		fairy = Fairy.create();
		person = fairy.person();
		Company company = fairy.company();
		if (testData.equals("mail"))
			return person.getEmail();
		if (testData.equals("username"))
			return person.getUsername();
		if (testData.equals("fullname"))
			return person.getFullName();
		if (testData.equals("firstName"))
			return person.getFirstName();
		if (testData.equals("password"))
			return person.getPassword();
		if (testData.equals("companyName"))
			return company.getName();
		if (testData.equals("address"))
			return person.getAddress().toString();
		return testData;
	}

	public String takeScreenShot(WebDriver driver, String screenshotName) throws IOException {
		File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String screenshotDestination = "./screenshot/" + screenshotName + ".png";
		FileUtils.copyFile(screenshot, new File(screenshotDestination));
		return screenshotDestination;
	}

	public void waitPageLoad(WebDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver, 120);
		wait.until(new Function<WebDriver, Boolean>() {
			public Boolean apply(WebDriver driver) {
				return String.valueOf(((JavascriptExecutor) driver).executeScript("return document.readyState"))
						.equals("complete");
			}
		});
	}

	public WebDriver openNewTab(WebDriver driver, String url) {
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("window.open('" + url + "','_blank');");
		return driver;
	}

}
