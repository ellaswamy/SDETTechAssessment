package UIHelpers;

import java.util.HashMap;

import org.apache.logging.log4j.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


public class UITestHelpers {

	protected static WebDriver driver;
	HashMap<String,String> driverPath=new HashMap<String,String>();
	private static Logger logger=LogManager.getLogger(UITestHelpers.class.getName());
	/*****
	 * Method to launch the browser
	 *@parameters: browser type, operating system
	 *@return: webdriver
	 **/
	public WebDriver launchBrowser(String browser,String os)
	{
		//get the driver path based on operating system
		switch(os.toLowerCase())
		{
			case "mac":
				logger.log(Level.INFO, "Setting Driver path for MAC OS");
				driverPath.put("chromeDriverPath","./resources/chromedriver_mac");
				driverPath.put("firefoxDriverPath","./resources/geckodriver_mac");
			break;
			case "windows":
				logger.log(Level.INFO, "Setting Driver path for Windows OS");
				driverPath.put("chromeDriverPath","./resources/chromedriver.exe");
				driverPath.put("firefoxDriverPath","./resources/geckodriver.exe");
			break;
			default:
				driverPath.put("chromeDriverPath","./resources/chromedriver.exe");
				driverPath.put("firefoxDriverPath","./resources/geckodriver.exe");
		}
		
		//launch the browser based on browser type
		setDriver(browser);
		return driver;
	}
	
	/*********
	 * Method to set the driver based on browser type
	 * @param browser
	 */
	public void setDriver(String browser)
	{
		switch(browser.toLowerCase())
		{
			case "chrome":
				logger.log(Level.INFO, "Launching Chromedriver");
				System.setProperty("webdriver.chrome.driver",driverPath.get("chromeDriverPath"));
				driver=new ChromeDriver();
			break;
			case "firefox":
				logger.log(Level.INFO, "Launching FirefoxDriver");
				System.setProperty("webdriver.gecko.driver",driverPath.get("firefoxDriverPath"));
				driver=new FirefoxDriver();
			break;
			default:
				System.setProperty("webdriver.chrome.driver",driverPath.get("chromeDriverPath"));
				driver=new ChromeDriver();
				break;
		}
		driver.manage().window().maximize();
	}
}
