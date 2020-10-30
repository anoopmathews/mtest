package com.sample.maven.mvntest;

import java.util.ArrayList;

import org.apache.commons.codec.digest.DigestUtils;
import org.testng.TestNG;

/**
 * Hello world!
 *
 */
public class App {

    
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	TestNG testng = new TestNG();
        ArrayList<String> suites = new ArrayList<String>();
        suites.add(System.getProperty("user.dir")+"\\config\\testng.xml");
        testng.setTestSuites(suites);
        testng.run();
		
	}

}
