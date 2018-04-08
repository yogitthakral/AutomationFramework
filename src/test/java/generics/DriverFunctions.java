package generics;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import core.SuiteConfig;

public class DriverFunctions extends SuiteConfig {

	public WebDriver driver;
	File file;
	public String Handle;

	public DriverFunctions(WebDriver driver){
		this.driver=driver;
	}

	public WebDriver startdriver(String driverName){
		String gridFlag = System.getenv("gridFlag");
		System.out.println("grid Flag is :"+gridFlag);

		if (("true").equals(gridFlag)) {
			
			
			
			intiateGridDriver(driverName);
			return driver;
		}

		if(driverName.equalsIgnoreCase("chrome"))
		{
			file= new File(win_chromeDriverPath);
			System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
			driver= new ChromeDriver();

		}

		else if(driverName.equalsIgnoreCase("firefox"))
		{
			file= new File(win_geckoDriverPath);
			System.setProperty("webdriver.gecko.driver", file.getAbsolutePath());
			driver= new FirefoxDriver();
		}

		else if(driverName.equalsIgnoreCase("phantomjs-windows"))
		{
			file= new File(win_phamtomJsDriverPath);
			System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
			driver= new PhantomJSDriver();
		}

		else if(driverName.equalsIgnoreCase("phantomjs-linux"))
		{
			file= new File(linux_phamtomJsDriverPath);
			System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
			driver= new PhantomJSDriver();
		}

		else if(driverName.equalsIgnoreCase("chrome-linux"))
		{
			file= new File(linux_chromeDriverPath);
			System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
			driver= new ChromeDriver();

		}

		return driver;

	}
	public void GetWindowHandle(){// to be modified
		Handle = driver.getWindowHandle();
	}
	public void SwitchtoOriginalWindow(){
		driver.switchTo().window(Handle);
	}
	
	public void SwitchtoNewWindow(){
		GetWindowHandle();
		for(String windowsHandle : driver.getWindowHandles()){
			driver.switchTo().window(windowsHandle);
		}
	}
	
	public void CloseNewWindow(){
		driver.close();
	}
	
	public void GoToSleep(int TimeInMillis)
	{
		try{Thread.sleep(TimeInMillis);} catch(Exception e){}
	}
	
	public void PerformMouseHover(String xpath){

		Actions builder=new Actions(driver);
		builder.moveToElement(driver.findElement(By.xpath(xpath)));
		builder.perform();	
	}
	
	
	public Boolean isVisible(String elementXpath)
	{
		if(driver.findElements(By.xpath(elementXpath)).size()!=0 && driver.findElement(By.xpath(elementXpath)).isDisplayed())
		{
		return true;
		}
		else
		{
		return false;
		}
	}
	
	public Boolean isPresent(String elementXpath)
	{


		SetImplicitWaitInSeconds(1);
		if(driver.findElements(By.xpath(elementXpath)).size()!=0)
		{
			return true;
		}
		else
		{
			return false;
		}


	}
	

	
	public void Accept_Alert(){
		Alert alert = driver.switchTo().alert();
		String text = alert.getText();
		alert.accept();
	}
	
	public void Dismiss_Alert(){
		Alert alert = driver.switchTo().alert();
		alert.dismiss();
	}
	
	
	public void quitDriver(){
		driver.quit();
	}
	
	public String TakeScreenshot(){
		String directory = System.getProperty("user.dir");
		directory = directory.replace("\\", "\\\\");

		String SaveName = Calendar.getInstance().getTime().toString().replace(":", "").replace(" ", "").trim();
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try {

			FileUtils.copyFile(scrFile, new File(directory+"\\screenshots\\"+SaveName+".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return SaveName+".png";
	}



	private void intiateGridDriver(String driverName) {

		ThreadLocal<RemoteWebDriver> threadDriver = null;

		threadDriver = new ThreadLocal<RemoteWebDriver>();

		if(driverName.equalsIgnoreCase("Chrome")){
			System.setProperty("webdriver.chrome.driver", "./../chromedriver.exe");
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();  
			System.out.println( "starting grid chrome driver");
			try {

				driver=new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
				((RemoteWebDriver) driver).setFileDetector(new LocalFileDetector());
				SetImplicitWaitInSeconds(10);
				threadDriver.set((RemoteWebDriver) driver);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}   
		}
	}

	public void SetImplicitWaitInSeconds(int timeOut){
		driver.manage().timeouts().implicitlyWait(timeOut, TimeUnit.SECONDS);
		System.out.println("Timeout set to "+timeOut+" seconds.");
	}

	public void SetPageLoadTimeoutInSeconds(int timeOut){
		driver.manage().timeouts().pageLoadTimeout(timeOut, TimeUnit.SECONDS);
		System.out.println("Timeout set to "+timeOut+" seconds.");
	}
	
	
	
	
	public void Fill(String xpath, String Value_To_Fill)

	{
		explicitWaitForSingleElement(xpath).sendKeys(Value_To_Fill);
	}


	public String Get(String xpath)
	{
		return explicitWaitForSingleElement(xpath).getText();
	}


	public void Select (String xpath, String Value_To_Select)

	{
		new Select(explicitWaitForSingleElement(xpath)).selectByVisibleText(Value_To_Select);
			}

	public void Click(String xpath)

	{
		explicitWaitForSingleElement(xpath).click();
	}
	
	public WebElement explicitWaitForSingleElement(String xPath){
		WebDriverWait wait = new WebDriverWait(driver, 30);
		return wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xPath)));
	}

	public void Clear(String xpath) {
		explicitWaitForSingleElement(xpath).clear();
		
	}

	public void Fill(String xpath, Keys keystoPress) {
		explicitWaitForSingleElement(xpath).sendKeys(keystoPress);
		
	}


	public void click_WebElementbyJavaScript(WebElement elementToBeClicked){
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0,"+elementToBeClicked.getLocation().y+")");
		elementToBeClicked.click();
	}


}
