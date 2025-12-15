package test.testCases;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import test.pageObjects.LoginPage;

@Listeners(TestListener.class)
public class LoginTests extends BaseClass {

	
    @Test(description="UI-001: Valid user logs in successfully")
    public void validLogin() {
        
        // Calls Log in 
        login(); 
        
        // Verify navigation to Inventory / Products page
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory.html"),
                "User did not navigate to Inventory page after login");
    }

    @Test(description="UI-002: Incorrect password displays error")
    public void invalidPasswordTest() {
        LoginPage loginPage = new LoginPage(driver);

        // Open login page first
        loginPage.getLoginPage();

        // Enter invalid credentials
        loginPage.setUserName("standard_user");
        loginPage.setPassWord("wrong");
        loginPage.clickSubmit();

        // Verify error message appears - Does not assert for exact string 
        Assert.assertTrue(loginPage.gerErrorMessage().length() > 0,
                "Error message not displayed for invalid password");
    }

    @Test(description="UI-003: Blank credentials produce error message")
    public void blankCredentialsTest() {
        LoginPage loginPage = new LoginPage(driver);

        // Open login page
        loginPage.getLoginPage();

        // Click submit without entering anything
        loginPage.clickSubmit();

        // Verify error message appears - Does not assert for exact string 
        Assert.assertTrue(loginPage.gerErrorMessage().length() > 0,
                "Error not displayed for blank credentials");
    }
}