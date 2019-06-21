package UIHelpers;

import org.apache.logging.log4j.*;
import org.testng.Assert;
import org.testng.annotations.*;

import libraries.ConfigSettings;


public class UIBaseTest extends UITestHelpers {
	
	private static Logger logger=LogManager.getLogger(UIBaseTest.class.getName());
	
	/***
	 * Method to initialize the UI tests by setting environment configuration parameters
	 */
	@BeforeTest
	public void testInitialize()
	{
		try
		{
			ConfigSettings.setUIConfigProperties();
			
			//Launch browser based on browser and os
			launchBrowser(ConfigSettings.configValues.get("browser"),ConfigSettings.configValues.get("os"));
			
			//Navigate to URI
			driver.get(ConfigSettings.configValues.get("uri"));
		}
		catch(Exception ex)
		{
			logger.log(Level.ERROR,ex);
			Assert.fail(ex.getMessage());
		}
	}
	
	//Method to close the driver
	@AfterTest
	public void tearDown()
	{
		driver.close();
	}
	
}
