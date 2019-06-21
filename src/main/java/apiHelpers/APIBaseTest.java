package apiHelpers;

import static io.restassured.RestAssured.given;

import java.io.IOException;

import org.apache.logging.log4j.*;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import libraries.ConfigSettings;

public class APIBaseTest {
	

	private static Logger log=LogManager.getLogger(APIBaseTest.class.getName());
	protected static RequestSpecification reqSpec;
	//method to initialize the test with required configurations
	@BeforeSuite
	public void testInitialization() throws IOException
	{
		log.info("Reading configuration properties");
		//Read the configuration properties
		ConfigSettings.setAPIConfigProperties();
		
		log.info("Setting the BaseURI");
		//Set up the BaseURI
		RestAssured.baseURI=ConfigSettings.configValues.get("APIBaseURI");
		
		reqSpec=APIHelpers.getReqSpec();
		//Set Guest Session Id
		setGuestSessionId();
		
	}
	
	public static void setGuestSessionId()
	{
		ConfigSettings.GuestSessionId=given()
		.spec(reqSpec)
		.when()
		.get("/authentication/guest_session/new")
		.then()
		.extract()
		.response().path("guest_session_id").toString();
	}
	
	@AfterSuite
	public static void tearDown()
	{
		RestAssured.reset();
	}

}
