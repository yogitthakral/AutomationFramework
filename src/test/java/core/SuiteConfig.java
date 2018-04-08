package core;

import org.testng.annotations.BeforeSuite;

public class SuiteConfig extends Constants {
	
	
	@BeforeSuite
	public void loadConfig(){
		loadconfiguration();// loads the properties file into properties_object
		drivertype = properties_Object.getProperty("drivertype");//sets the constant variables 
		System.out.println(drivertype);
		
		projectname = properties_Object.getProperty("projectname");				
		System.out.println(projectname);
		
		planname = properties_Object.getProperty("planname");
		System.out.println(planname);
	}

}
