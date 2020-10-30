package com.sample.maven.mvntest;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;
import org.testng.asserts.SoftAssert;

import com.sample.maven.utilities.BrowserUtils;
import com.sample.maven.utilities.TimerUtils;

import static io.restassured.RestAssured.given;

public class tests{
	
	static{
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
	}
	
	static WebDriver driver=null;
	@BeforeTest
    public void beforeTest(){
        System.out.println("From BeforeTest annotation in tests class ..");
    }

    @BeforeSuite
    public void beforeSuite(){
        System.out.println("From BeforeSuite annotation in tests class ..");
        driver=BrowserUtils.initBrowser(driver);
    }
    
    @BeforeClass
    public void beforeClass()
    {
        System.out.println("From BeforeClass annotation test in tests class....");
    }
    
    @BeforeMethod
    public void beforeMethod()
    {
        System.out.println("From BeforeMethod annotation in tests....");
    }
    
    
    @AfterMethod
    public void AfterMethod()
    {
        System.out.println("From AfterMethod annotation in tests....");
    }
    
    @AfterClass
    public void afterClass()
    {
        System.out.println("From AfterClass annotation test in tests class....");
    }

    @AfterTest
    public void afterTest()
    {
        System.out.println("From AfterTest annotation in tests class ....");
    }

    @AfterSuite
    public void afterSuite()
    {
        System.out.println("From AfterSuite annotation in tests class ....");
        driver.quit();
    }
	
    @Test
    @Parameters("startCounter")
	public void testTimer(String startCounter){
    	Assertion hardAssert = new Assertion();
		SoftAssert softAssert = new SoftAssert();
		
    	System.out.println("Thread::" + Thread.currentThread().getName()+" in testTimer");
    	driver.navigate().to("https://e.ggtimer.com");
        BrowserUtils.waitTillPageReady(driver);
        WebElement we =driver.findElement(By.name("start_a_timer"));
        we.clear();
        int counter=Integer.valueOf(startCounter);
        we.sendKeys(Integer.toString(counter));
        String msg="";
                
        
        //store the count down numbers
        List<Integer> l = new ArrayList<Integer>();
        //store missing numbers
        List<Integer> l1 = new ArrayList<Integer>();
        
        TimerUtils.initiateTimerWait(driver);
        
        driver.findElement(By.id("timergo")).click();
        we =driver.findElement(By.id("progressText"));
        
        
        while(!msg.contains("Expired") && counter>0){
        	//System.out.println(counter);
        	try{
        		msg=TimerUtils.checkCountdownProgress(driver, counter);
        	}catch(Exception e){}
        	System.out.println(msg);
        	try{
        	l.add(Integer.valueOf(msg.split(" ")[0]));
        	}catch(Exception e){}
        	//System.out.println(counter + ":: "+val);
        	counter--;
        }
        
       
    	try {
			Thread.sleep(2000);
			driver.switchTo().alert().accept();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
        
        //after timer has ended, check for missing numbers
        int c=Integer.valueOf(startCounter);
        while(c>0){
        	if(!l.contains(c--))
        		l1.add(c+1);
        }
        
        System.out.println("## Missing numbers ##");
        for(Integer k:l1){
        	System.out.println(k);	
        }
        
        if(l1.size()>0)
        	softAssert.assertTrue(false, "The countdown decrement is not happening at an interval of 1 second");

        if(l1.size()==0)
        	softAssert.assertTrue(true, "The countdown decrement is happening at an interval of 1 second");

        softAssert.assertAll();
        
        /* For checking if all the count down messages are displayed for all the numbers in the range beginTimerValue to 1,
         * another test can be created to capture the progressText element using text value and check its visibility at smaller intervals than 1 second
         */
        
	}
    
	@Test
	public void testSpaceXEndpoint(){
		
		System.out.println("Thread::" + Thread.currentThread().getName()+" in testSpaceXEndpoint");
		Reporter.log("Inside testSpaceXEndpoint test");
		
		Assertion hardAssert = new Assertion();
		SoftAssert softAssert = new SoftAssert();
		
		String url="https://api.spacexdata.com/v4/launches/latest";
		
		Response response= given()		//relaxedHTTPSValidation("TLSv1.2")
		   		.contentType("application/json")
		   		.log()
		   		.all()					
		   		.when()
		   		.get(url)
		   		.then().log().all().extract().response();
		
		System.out.println("Status : "+response.getStatusCode());
		Reporter.log("Response Status - "+response.getStatusCode());
		hardAssert.assertEquals(response.getStatusCode(), 200, "Response code is not 200. Actual - "+response.getStatusCode());
		
		//System.out.println(response.asString());
		System.out.println("Response Time : " + response.getTime());
		int resTime=Integer.parseInt(response.getHeader("spacex-api-response-time").substring(0,response.getHeader("spacex-api-response-time").length()-2));
		Reporter.log("Response time - "+resTime);
		softAssert.assertTrue(resTime < 2000 , "Response took more than 2 seconds" );
		
		JSONParser jsonParser = new JSONParser();
		JSONObject obj = null;
		try {
			obj = (JSONObject) jsonParser.parse(response.asString());
			
			softAssert.assertTrue(obj.containsKey("id"), "id field is not present" );
			if(obj.containsKey("id")){
				System.out.println("ys");
				
			}
			String id= (String)obj.get("id");
			softAssert.assertTrue(id!=null, "id field is null" );
			if(id!=null)
				System.out.println("ys");
            
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Reporter.log("Exception : "+e.getMessage());
		}
		 softAssert.assertAll();
		
	}
	
	
	
}