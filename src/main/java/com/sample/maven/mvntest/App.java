package com.sample.maven.mvntest;

import java.util.ArrayList;

import org.apache.commons.codec.digest.DigestUtils;
import org.testng.TestNG;

/**
 * Hello world!
 *
 */
public class App {

    /*public static void main(String[] args) {

        if (args.length < 1) {
            System.err.println("Please provide an input!");
            System.exit(0);
        }
        System.out.println(sha256hex(args[0]));

    }

    public static String sha256hex(String input) {
        return DigestUtils.sha256Hex(input);
    }*/
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		TestNG testng = new TestNG();
        ArrayList<String> suites = new ArrayList<String>();
        suites.add(System.getProperty("user.dir")+"\\config\\testng.xml");
        testng.setTestSuites(suites);
        testng.run();
		
	}

}
