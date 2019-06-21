package pageObjects;

import org.openqa.selenium.By;

public class HomePage {
	
	public static By shoppingCartDivSelector=By.id("shopping_cart_container");
	//public static By addToCartButtonSelector=By.xpath("//button[@class='btn_primary btn_inventory'] and [text()='ADD TO CART']");
	public static By cartContainerSelector=By.xpath("//div[@id='shopping_cart_container']//span[contains(@class,'shopping_cart_badge')]");
}
