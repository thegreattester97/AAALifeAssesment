package test.testCases;

import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

import io.github.bonigarcia.wdm.WebDriverManager;
import test.pageObjects.LoginPage;
import test.pageObjects.ProductsPage;

@Listeners(TestListener.class)
public class BaseClass {
	
	
	// This Serves as a Consistent starting point and ending point for all executed tests 
    // Dev Environment essentials
    public String testSite = "https://www.saucedemo.com/";
    public static String userName = "standard_user";
    public static String passWord = "secret_sauce";
    public static Logger logger;

    // Page Declarations
    protected WebDriver driver;
    protected LoginPage loginPage;
    protected ProductsPage productsPage;

    public WebDriver getDriver() {
        return driver;
    }

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
    WebDriverManager.chromedriver().setup();
        
        // Initialize FirefoxDriver
        driver = new ChromeDriver();

        // Maximize browser window
        driver.manage().window().maximize();
        	
        // Initialize Page Objects
        loginPage = new LoginPage(driver);
        productsPage = new ProductsPage(driver);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    // Login helper method
    public void login() {
        loginPage.getLoginPage();
        loginPage.setUserName(userName);
        loginPage.setPassWord(passWord);
        loginPage.clickSubmit();
    }
    
    // Screenshot Method
    public void takeScreenshot(String name) {
        new TestListener().takeScreenshot(name);
    }

    // HTML Dump Method 
    public void saveHtml(String name) {
        new TestListener().saveHtml(name);
    }
}