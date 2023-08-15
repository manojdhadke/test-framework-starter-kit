package com.demo.learning.googlesearch;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.demo.learning.utility.ApplicationUtility;
import com.demo.learning.utility.Static;

public class SearchAutomation extends ApplicationUtility {

	WebDriver driver;
	SearchAutomationPageObject searchAutomationPageObject;

	@BeforeClass
	public void open() {
		driver = init(getPropertyFileData("url"));
		searchAutomationPageObject = PageFactory.initElements(driver, SearchAutomationPageObject.class);

	}

	@Test
	public void testGoogleSearch() {

		extentTest = extentReports.createTest("Sorting Roles in User Management");
		assertEquals(Static.SUCCESS, searchAutomationPageObject.searchAutomation(getPropertyFileData("seachKeyword")));

	}

}
