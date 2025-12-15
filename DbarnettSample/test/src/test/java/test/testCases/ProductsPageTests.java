package test.testCases;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(TestListener.class)
public class ProductsPageTests extends BaseClass {

    @Test(description = "UI-004: Verify products list is visible after login")
    public void verifyProductsAreDisplayed() {
    	// Calls login 
        login();
        
        // Asserts the products page loads successfully 
        Assert.assertTrue(productsPage.isPageLoaded(),
                "Products page did not load successfully.");
        // Asserts product(s) are present on the page 
        Assert.assertTrue(productsPage.getProductCount() > 0,
                "No products were found on the products page.");
        
        // displays prodcu count on the console 
        System.out.println(productsPage.getProductCount());
    }

    @Test(description = "UI-005: Add item increments cart")
    public void verifyAddItemUpdatesCart() {
    	// Calls login 
        login();
        
        // Checks count & add first item into the cart 
        int initialCount = productsPage.getCartCountSafely();
        productsPage.addFirstItemToCart();
        
        
        // Check for count to be one greater than before the item was added 
        Assert.assertEquals(productsPage.getCartCountSafely(), initialCount + 1,
                "Cart badge was not incremented after adding item");
    }

    @Test(description = "UI-006: Added item appears in cart list")
    public void verifyItemVisibleInCart() {
    	// Calls login
        login();
        
        // Checls the name of the first item & add to cart
        String itemName = productsPage.getFirstProductName();
        productsPage.addFirstItemToCart();
        productsPage.clickCart();
        
        // Asserts that the item has the same name as item expected
        Assert.assertTrue(productsPage.getCartItemName().contains(itemName),
                "Cart does not contain the item previously added");
    }

	
}