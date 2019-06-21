package libraries;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import org.apache.logging.log4j.*;
import org.junit.Assert;


/***
 * ConfigSettings class is to read the environment config properties from properties file
 * Set the config variables for API and UI
 *@author swamy
 */
public class ConfigSettings {
	
	public static String GuestSessionId;
	
	public static HashMap<String,String> configValues=new HashMap<String,String>();
	
	private static Logger logger=LogManager.getLogger(ConfigSettings.class.getName());
	
	static Properties prop=new Properties();
	
	 //method to load properties from properties file
	static void loadProperties() throws IOException
	{
		try
		{
			File file = new File("./resources/EnvironmentConfig.properties");
			FileInputStream fileInput = new FileInputStream(file);
			// Load the Property file available in same package
			prop.load(fileInput);
		}
		catch(FileNotFoundException fnfe)
		{
			logger.log(Level.ERROR,fnfe);
		}
		catch(IOException ioe)
		{
			logger.log(Level.ERROR,ioe);
		}
		catch(Exception e)
		{
			logger.log(Level.ERROR,e);
		}
	}
	
	//method to set API environment configuration parameters
	public static void setAPIConfigProperties() throws IOException
	{
		try
		{
			loadProperties();
			configValues.put("api_key",prop.getProperty("api_key"));
			configValues.put("APIBaseURI",prop.getProperty("apiBaseURI"));
		}
		catch(Exception ex)
		{
			logger.log(Level.ERROR,"Logged Exception",ex);
			Assert.fail(ex.getMessage());
		}
	}
	
	//method to set UI environment configuration parameters
	public static void setUIConfigProperties()
	{
		try
		{
			loadProperties();
			configValues.put("uri",prop.getProperty("uri"));
			configValues.put("browser",prop.getProperty("browser"));
			configValues.put("os",prop.getProperty("operatingSystem"));
		}
		catch(Exception ex)
		{
			logger.log(Level.ERROR,"Logged Exception",ex);
			Assert.fail(ex.getMessage());
		}
	}
}
