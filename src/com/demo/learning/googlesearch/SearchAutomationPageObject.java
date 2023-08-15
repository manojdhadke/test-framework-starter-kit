package com.demo.learning.googlesearch;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.demo.learning.utility.ApplicationUtility;

public class SearchAutomationPageObject extends ApplicationUtility{
	
	WebDriver driver = null;
	WebDriverWait wait = null;

	
	public SearchAutomationPageObject(WebDriver driver) {
		this.driver = driver;
	}
	
	@FindBy(xpath="//*[@id=\"tsf\"]/div[2]/div/div[1]/div/div[1]/input")
	WebElement searchBar;
	
	public int searchAutomation(String searchKeyword)
	{
		waitPageLoad(driver);
		searchBar.sendKeys(searchKeyword);
		waitPageLoad(driver);
		return 200;
		
	}
	
	

}
