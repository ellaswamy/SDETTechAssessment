package ui_tests;

import java.io.IOException;

import org.apache.logging.log4j.*;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import UIHelpers.SeleniumActions;
import UIHelpers.UIBaseTest;
import libraries.TestDataHelper;
import pageObjects.HomePage;
import pageObjects.LoginPage;

public class AddToCartTests extends UIBaseTest{
	
	private static Logger logger=LogManager.getLogger(AddToCartTests.class.getName());
	
	/***
	 * addToCart test is to validate the "Add To Cart" functionality 
	 * @param userName
	 * @param password
	 */
	@Test(dataProvider="Login")
	public void addToCart(String userName,String password)
	{
		try
		{
			//Login to the website using userName and password
			//userName and password are read from test data excel
			login(userName,password);
			
			//Verify if Products text is displayed on HomePage on successful login
			Assert.assertTrue(SeleniumActions.isDisplayed(HomePage.shoppingCartDivSelector),"Login is not successful");
			
			logger.log(Level.INFO,"Getting products from excel that need to be added to the cart");
			Object[][] productNames= TestDataHelper.getTestData("CartItems");
			
			//Iterate through products list
			for(int index=0;index<productNames.length;index++)
			{
				logger.log(Level.INFO,"Clicking on add to cart for respective product..");
				SeleniumActions.click(SeleniumActions.getAddToCartByTextIndex(productNames[index][0].toString()));
			}
			
			logger.log(Level.INFO,"Verifing the items in the cart...");
			
			//Once the items are added to the cart, verify if cart container is displayed
			Assert.assertTrue(SeleniumActions.isDisplayed(HomePage.cartContainerSelector),"Cart container is not displayed");
			
			//Verify the number of items in the cart
			Assert.assertEquals(Integer.parseInt(SeleniumActions.getText(HomePage.cartContainerSelector)),productNames.length,
					"Number of items in the cart are not matching with the number of added items");
		}
		catch(Exception ex)
		{
			logger.log(Level.ERROR,"Exception occured in Login Tests",ex);
			Assert.fail(ex.getMessage());
		}
			
	}
	
	//Method to login to the saucedemo website
	public void login(String userName,String password)
	{
		SeleniumActions.enterText(LoginPage.userNameTextSelector,userName);
		SeleniumActions.enterText(LoginPage.passwordTextSelector,password);
		SeleniumActions.click(LoginPage.loginButtonSelector);
	}
	
	//Get the login data from excel
	@DataProvider(name="Login")
	public Object[][] getLoginData() throws IOException
	{
		return TestDataHelper.getTestData("Login");
	}
}
