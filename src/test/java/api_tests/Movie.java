package api_tests;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.*;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import apiHelpers.APIBaseTest;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import libraries.ConfigSettings;
import libraries.TestDataHelper;

public class Movie extends APIBaseTest {
	
	private static Logger logger=LogManager.getLogger(Movie.class.getName());
	/*Test to validate the "POST" method functionality for "Movie/Rate Movie" end point to rate a movie*/
	
	//Test Case 8: Positive Test Case: Validate POST call for Rate Movie end point with valid rating value
	//Test Case 9: Negative Test Case: Validate POST call for Rate Movie end point with invalid rating value (such as 9.1, >10.0 or <0.5 and also for boundary values)
	@Test (dataProvider="Rate_Movie")
	public void rateAMovie(int movieId,double rating,int expectedStatusCode)
	{
			try
			{
				RateMovie rate=new RateMovie();
				rate.setValue(rating);
				Response response=given()
						.spec(reqSpec)
						.queryParam("guest_session_id", ConfigSettings.GuestSessionId)
						.pathParam("movie_id", movieId)
						.contentType(ContentType.JSON)
						.when()
						.body(rate)
						.post("/movie/{movie_id}/rating")
						.then()
						.extract()
						.response();
				
				if(expectedStatusCode!=response.statusCode())
					throw (new Exception(new JsonPath(response.asString()).get("status_message").toString()+"for movie:"+String.valueOf(movieId)));
				if(response.statusCode()==201)
				{
					//Get the posted rating for the movies and validate the post functionality
					List<HashMap<String,String>> response2=
								given()
								.spec(reqSpec)
								.pathParam("guest_session_id", ConfigSettings.GuestSessionId)
								.get("/guest_session/{guest_session_id}/rated/movies")
								.then()
								.extract().path("results.findAll{it.id=="+movieId+"}");
						if(response2.size()==0)
							throw (new Exception("Movie was not found in the response"));
						if(!(String.valueOf(response2.get(0).get("rating")).equals(String.valueOf(rating))))
								throw (new Exception("Rating is mismatched for movie id:"+String.valueOf(movieId)));
				}
			}
			catch(Exception ex)
			{
				logger.log(Level.ERROR,"Logged Exception",ex);
				Assert.fail(ex.getMessage());
			}
			
			
		}
	
	/*Test to validate the DELETE functionality of DELETE Rating "Movie/Delete Rating" end point
	 * @parameters: movieId --> movie id for which the rating has to be deleted
	 * @return: void
	 * */
	
	//Test Case 10: Positive Test Case: Validate the DELETE call for movie rating end point
	//Test Case 11: Negative Test Case: Validate the DELETE call for movie rating end point for the movie that doesn't exist in the database
	//Test Case 12: Negative Test Case: Validate the DELETE call for Movie end point without passing movie Id value
	@Test (dataProvider="Delete_Movie")
	public void deleteRating(int movieId,int expectedStatusCode)
	{
		try
		{
			Response response=
					given()
					.spec(reqSpec)
					.queryParam("guest_session_id", ConfigSettings.GuestSessionId)
					.pathParam("movie_id", movieId)
					.delete("/movie/{movie_id}/rating")
					.then()
					.extract().response();
			
			if(expectedStatusCode!=response.statusCode())
				throw (new Exception (new JsonPath(response.asString()).get("status_message").toString()+"for movie:"+String.valueOf(movieId)));
		}
		catch(Exception ex)
		{
			logger.log(Level.ERROR,"Logged Exception",ex);
			Assert.fail(ex.getMessage());
		}
		
	}
	
	//Test Case 13: Negative Test Case: Validate DELETE call status message for movie rating end point without guest session id
	@Test (dataProvider="Delete_Movie")
	public void deleteRatingWithOutGuestSession(int movieId,int expectedStatusCode)
	{
		try
		{
			logger.info("Calling DELETE Rating end point without guest session");
			Response response=
					given()
					.spec(reqSpec)
					.pathParam("movie_id", movieId)
					.delete("/movie/{movie_id}/rating")
					.then()
					.extract().response();
			
			logger.info("Validating the status message");
			if(!(new JsonPath(response.asString()).get("status_message").toString().equals("Authentication failed: You do not have permissions to access the service.")))
				throw (new Exception("Invalid error message calling Delete end point withoutn guest session"));
		}
		catch(Exception ex)
		{
			logger.log(Level.ERROR,"Logged Exception",ex);
			Assert.fail(ex.getMessage());
		}
		
	}
	
	//Test Case 14: Validate the response time against the specifications
	@Test
	public void validateResponseTime()
	{
		//ToDo
		//Validate response time of end point against specifications
	}
	
	
	@DataProvider(name = "Rate_Movie")
	  public Object[][] rateMovie() throws Exception {
		logger.info("Reading Rate Movie test data from dataprovider...");
		return TestDataHelper.getTestData("Rate_Movie");
	}
	
	@DataProvider(name = "Delete_Movie")
	  public Object[][] deleteMovie() throws Exception {
		logger.info("Reading Delete Movie test data from dataprovider...");
		return TestDataHelper.getTestData("Delete_Movie");
	}
}
