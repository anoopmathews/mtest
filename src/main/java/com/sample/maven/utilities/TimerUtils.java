package com.sample.maven.utilities;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import com.google.common.base.Function;

public class TimerUtils {

	static Wait<WebDriver> wait;
	
	static String msg="";
	
	/*This method initializes wait specifically to be used during the count down progression 
	 * inside checkCountdownProgress method
	 */
	public static void initiateTimerWait(WebDriver driver){
		wait = new FluentWait<>(driver)
                .pollingEvery(Duration.ofMillis(1000))
                .withTimeout(Duration.ofMillis(1000))
                .ignoring(org.openqa.selenium.NoSuchElementException.class);
	}
	
	
	//This method retrieves the count down message every second
	public static String checkCountdownProgress(WebDriver driver, int counter){
		
		Function<WebDriver, Boolean> changeCount = new Function<WebDriver, Boolean>(){
	        public Boolean apply(WebDriver arg0) {
	        	try{
    		        WebElement element = arg0.findElement(By.id("progressText"));
    		        String txt = element.getText();
    		        //System.out.println(txt);
    		        msg=txt;
    		        //((JavascriptExecutor)arg0).executeScript("alert('"+msg+"')");
    		        if(txt.contains(Integer.toString(counter)))
    		        {
    		        return true;
    		        }
    		        return false;
	        	}
	        	catch(UnhandledAlertException e){
	        		arg0.switchTo().alert().accept();
	        		return false;
	        	}
	        }
        };
		
        wait.until(changeCount);
        
        return msg;
	}
	
}
