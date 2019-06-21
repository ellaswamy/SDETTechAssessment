package pageObjects;

import org.openqa.selenium.By;

public class LoginPage {
	public static By userNameTextSelector=By.id("user-name");
	public static By passwordTextSelector=By.id("password");
	public static By loginButtonSelector=By.cssSelector("input[value='LOGIN']");
}
