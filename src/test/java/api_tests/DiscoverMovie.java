package api_tests;


import static io.restassured.RestAssured.given;

import java.io.IOException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import apiHelpers.APIBaseTest;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import libraries.TestDataHelper;

public class DiscoverMovie extends APIBaseTest{
	
	private static Logger logger=LogManager.getLogger(DiscoverMovie.class.getName());
	
	
	//1.	Positive Test: Validate the response status code returned by Discover Movie end point for successful GET request (200 should be returned)
	//2.	Positive Test: Validate the response data returned by Discover Movie end point for successful GET request 
	//3.	Positive Test: Verify that the content type of the response returned by the Discover Movie end point should be JSON only.
		@Test
		public void discoverAllMovies()
		{
			try
			{
				logger.info("Calling Discover/Movie end point and retrieving the response");
				Response response=given()
					.spec(reqSpec)
					.when()
					.get("/discover/movie")
					.then()
					.extract()
					.response();
			
				Assert.assertEquals(response.statusCode(),200,"Invalid status code for sucessful end point calling");
				
				logger.info("validating if the response is null");
				Assert.assertFalse(response==null,"Response is empty");
				
				logger.info("validating the content type");
				response.then().assertThat().contentType(ContentType.JSON);
			}
			catch(Exception ex)
			{
				logger.log(Level.ERROR,"Logged Exception",ex);
				Assert.fail(ex.getMessage());
			}
		}
		
		//Test Case 4: Negative Test Case: Validate the error response status code returned by Discover Movie end point by providing incorrect URL for GET request 
		@Test
		public void getDiscoverMovie()
		{
			try
			{
				Response response=given()
						.spec(reqSpec)
						.when()
						.get("/discover/movei")
						.then()
						.extract()
						.response();
				
				if(response.statusCode()!=404)
					throw (new Exception("Invalid status code for incorrect end point calling"));
			}
			catch(Exception ex)
			{
				logger.log(Level.ERROR,"Logged Exception",ex);
				Assert.fail(ex.getMessage());
			}
		}
		
		//Test Case 5: Negative Test Case: Validate the error status code returned by Discover Movie end point without authorization key 
		@Test
		public void getDiscoverMovie_WithNoAuthorization()
		{
			try
			{
				Response response=given()
						.when()
						.get("/discover/movie")
						.then()
						.extract()
						.response();
				
				if(response.statusCode()!=401)
					throw (new Exception ("Invalid status code for wihout authorization key GET call"));
			}
			catch(Exception ex)
			{
				logger.log(Level.ERROR,"Logged Exception",ex);
				Assert.fail(ex.getMessage());
			}
		}
	
	/*Test Case 6: Validate the GET functionality of /discover/movie end point by applying filters on release year and vote count
	 *can be extended for multiple parameters
	 * Read the data from data provider and repeat the test for multiple data sets
	 * 
	 * Test Case 7: Negative Test Case: Validate the GET call for Discover/Movie end point for the movie details by passing invalid parameter values as filters e.g.voteCount, 
	 *empty body response, null values as parameters
	 */
	@Test (dataProvider="Movie_Discover")
	public void getDiscoverMovie_WithParameters(int releaseYear,int voteCount) throws IOException
	{
		try
		{
			//GET the response from /discover/movie end point
			Response response=
					given()
					.spec(reqSpec)
					.queryParam("primary_release_year", releaseYear)
					.queryParam("vote_count.gte", voteCount)
					.when()
					.get("/discover/movie")
					.then()
					.extract()
					.response();
			String errorMessage="";
			if(response.statusCode()!=200)
				errorMessage=new JsonPath(response.asString()).get("status_message").toString()+"for voteCount:"+String.valueOf(voteCount);
			Assert.assertEquals(response.statusCode(),200,errorMessage);
			
			JsonPath json=new JsonPath(response.asString());
			
			//get the total number of results in the response
			int totalResults=json.getInt("total_results");
			
			//validate the release year and vote count in the response by comparing with input data
			//to make sure returned response is based on applied filters
			for(int index=0;index<totalResults;index++)
			{
				String releaseDate=response.path("results["+index+"].release_date").toString();
				if(Integer.parseInt(releaseDate.substring(0, releaseDate.indexOf("-")))!=releaseYear)
					throw new Exception("Release Year is not matching");
				if((Integer)response.path("results["+index+"].vote_count")<voteCount)
				 throw new Exception("Vote count is not matching");
			}
		}
		catch(Exception ex)
		{
			logger.log(Level.ERROR,"Logged Exception",ex);
			Assert.fail(ex.getMessage());
		}
	}
	
	//Test Case 8: Test to validate the content of the Movie discover end point response with the expected response data
	@Test
	public void validateDiscoverMovieData()
	{
		//TO DO actions
		//Get the response by using "GET" call to "discover/movie" end point
		//Read the expected response JSON data from external file and compare it with actual JSON response data
	}
	
	
	//Test Case 9: Test to validate the sorting functionality of the Discover/Movie end point "GET" response
	@Test(dataProvider="Sorting")
	public void validateSortedDiscoverMovieData(String sortingParameter)
	{
		//TO DO actions
		//Get the response by using "GET" call to "discover/movie" end point
		//Compare the response with sorted expected data
	}
	
	
	@DataProvider(name = "Movie_Discover")
	  public Object[][] MovieDiscover() throws Exception {
		logger.info("Reading Movie Discover test data from dataprovider...");
		return TestDataHelper.getTestData("Movie_Discover");
	}
	
	@DataProvider(name = "Sorting")
	  public Object[][] sortedMovies() throws Exception {
		logger.info("Reading Sorted Movies test data from dataprovider...");
		//Get sorting parameters from excel
		return new Object[0][0];
	}

}

