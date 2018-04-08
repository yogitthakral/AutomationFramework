package test_cases;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import core.SuiteConfig;
import core.Urls;
import generics.DriverFunctions;



public class PingdomTest extends SuiteConfig {
	DriverFunctions genericFn;
	Urls url;
	
	@BeforeMethod
	public void init_driver()
	{
		genericFn= new DriverFunctions(driver);
		driver=genericFn.startdriver(drivertype);
		url= new Urls();	
	}

@Test
	public 	void test1()
	{
		//Scripting Start
//	Assert.assertTrue(false);
	driver=null;
		driver.get("http://tools.pingdom.com/ping");
	

	}

@AfterMethod
public void teardown(){
	genericFn.quitDriver();
}


}