package listeners;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.*;
import org.testng.TestListenerAdapter;

import core.Constants;

public class TestListeners extends TestListenerAdapter 
{

	 
		private static String fileSeperator = System.getProperty("file.separator");

		@Override
		public void onTestFailure(ITestResult result) {
			System.out.println("***** Error " + result.getName() + " test has failed *****");
			

			String testClassName = getTestClassName(result.getInstanceName()).trim();

			String testMethodName = result.getName().toString().trim();
			
			System.out.println(testClassName +" is the class and method is "+testMethodName);

		}

		

		public String getTestClassName(String testName) {
			String[] reqTestClassname = testName.split("\\.");
			int i = reqTestClassname.length - 1;
			System.out.println("Required Class Name : " + reqTestClassname[i]);
			return reqTestClassname[i];
		}


	 @Override
	 public void onTestSkipped(ITestResult tr) {
		  System.out.println("Skipped*************"+tr.getName()+"**************");
	  
	 }

	 @Override
	 public void onTestSuccess(ITestResult tr) {
		  System.out.println("Passed****************"+tr.getName()+"*******************");
	 }
	 
	 
	 
	 public void onFinish(ITestContext context) {}
	  
	    public void onTestStart(ITestResult result) {   }

	    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {   }

	    public void onStart(ITestContext context) {   }

	}

