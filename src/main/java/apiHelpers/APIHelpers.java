package apiHelpers;

import org.apache.logging.log4j.*;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import libraries.ConfigSettings;

public class APIHelpers {
	static RequestSpecBuilder builder=new RequestSpecBuilder();
	private static Logger logger=LogManager.getLogger(APIHelpers.class.getName());
	public static RequestSpecification getReqSpec()
	{
		logger.log(Level.INFO, "Getting Request Specification");
		return builder.addQueryParam("api_key", ConfigSettings.configValues.get("api_key")).build();
	}
}
