package UIHelpers;

import org.apache.logging.log4j.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

/*****
 * SeleniumActions class provides the methods that can be performed on Selenium elements
 * @author swamy
 *
 */
public class SeleniumActions extends UITestHelpers{
	
	private static Logger logger=LogManager.getLogger(SeleniumActions.class.getName());
	
	//Method to enter text into text box
	public static void enterText(By webElement,String text)
	{
		try
		{
			if(waitForElementVisible(webElement)!=null)
				driver.findElement(webElement).sendKeys(text);
		}
		catch(Exception e)
		{
			logger.log(Level.ERROR,"Logged Exception",e);
			Assert.fail("Exception Occured while entering text "+e.getMessage());
		}
	}
	
	//Method to perform click opeation
	public static void click(By webElement)
	{
		try
		{
			if(waitForElementClickable(webElement)!=null)
				driver.findElement(webElement).click();
		}
		catch(Exception e)
		{
			logger.log(Level.ERROR,"Logged Exception",e);
			Assert.fail("Exception Occured while entering text "+e.getMessage());
		}
	}
	
	//Explicit wait method to wait for an element to be visible
	//For every 5 seconds the element will be polled for visibility check
	public static WebElement waitForElementVisible(By webElement)
	{
		try
		{
			//Max wait is 60 seconds
			WebDriverWait wait=new WebDriverWait(driver,60);
		 
		// Wait till the element is not visible
			return wait.until(ExpectedConditions.visibilityOfElementLocated(webElement));
		}
		catch(Exception e)
		{
			logger.log(Level.ERROR,"Logged Exception",e);
			Assert.fail("Exception Occured while waiting for element "+e.getMessage());
		}
		return null;
	}
	
	//Explicit wait method to wait for an element to be clickable
	//For every 5 seconds the element will be polled for visibility check
	public static WebElement waitForElementClickable(By webElement)
	{
		try
		{
			WebDriverWait wait=new WebDriverWait(driver,20);
		 
		// Wait till the element is not visible
			return wait.until(ExpectedConditions.elementToBeClickable(webElement));
		}
		catch(Exception e)
		{
			logger.log(Level.ERROR,"Logged Exception",e);
			Assert.fail("Exception Occured while waiting for element "+e.getMessage());
		}
		return null;
	}
	
	//Method is to verify if an element is present in webpage
	public static Boolean isDisplayed(By webElement)
	{
		waitForElementVisible(webElement);
		if(driver.findElements(webElement).size()>0)
			return true;
		else
			return false;
	}
	
	//Method to get the add to cart element path by using element text
	public static By getAddToCartByTextIndex(String elementText)
	{
		return By.xpath("//div[./a[./div[text()='"+elementText+"']]]/following-sibling::div//button");
	}
	
	//Method to get the text of a web element
	public static String getText(By webElement)
	{
		return driver.findElement(webElement).getText();
	}
}
