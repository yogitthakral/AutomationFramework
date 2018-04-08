package core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;



public class Constants {

	//Absolute variables or constants that will be used project wide
	public Properties properties_Object=new Properties();
	public WebDriver driver;

	//variables mapped with config.properties so that they can be replaced by SuiteConfig Class at the runtime.
	public String drivertype="chrome";
	public String projectname="project";
	public String planname="plan";




	//driver executable paths
	public String win_chromeDriverPath="D:\\chromedriver.exe";
	public String win_geckoDriverPath="";
	public String win_ieDriverPath="";
	public String win_phamtomJsDriverPath="";


	public String linux_chromeDriverPath="";
	public String linux_geckoDriverPath="";
	public String linux_ieDriverPath="";
	public String linux_phamtomJsDriverPath="";




	//


	public WebDriver getDriver() {
		return this.driver;//returns driver object
	}



	public void loadconfiguration()//will be called in Suiteconfig to load variables in properties objects and then replacing the mapped variables.
	{
		try {
			FileInputStream fi= new FileInputStream(".//src//test//java//config.properties");//Give file path into newly created file input stream project
			properties_Object.load(fi);//load fileinput stream project into Properties Object.
		} 

		catch (Exception e) 
		{
			System.out.println(e.getMessage());
		}
	}	



	public String getPropertyValue(String propertyName) {
		String propFileName = ".//src//test//java//config.properties";
		File file = new File(propFileName);
		FileInputStream fileInput = null;
		try {
			fileInput = new FileInputStream(file);
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Properties prop = new Properties();
		try {
			prop.load(fileInput);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		String propertyValue = prop.getProperty(propertyName);
		return propertyValue;
	}






}
