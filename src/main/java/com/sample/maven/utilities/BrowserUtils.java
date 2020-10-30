package com.sample.maven.utilities;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BrowserUtils {

	public static WebDriver initBrowser(WebDriver driver){
		
		ChromeOptions opt = new ChromeOptions();
        opt.setCapability("pageLoadStrategy", "none");
        driver = new ChromeDriver(opt); 
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        return driver;
	}
	
	
	public static void waitTillPageReady(WebDriver driver){
		WebDriverWait wait1 = new WebDriverWait(driver, 30);
		
		ExpectedCondition<Boolean> ex = new ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver driver) {
                        return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
                    }
                };
		
		wait1.until(ex);
	}
}
