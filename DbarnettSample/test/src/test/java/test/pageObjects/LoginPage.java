package test.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class LoginPage {
    
    static WebDriver ldriver;
    public static WebDriverWait wait;
    
    // Constructor 
    public LoginPage(WebDriver rdriver) {
        ldriver = rdriver;
        wait = new WebDriverWait(ldriver, Duration.ofSeconds(10)); // 10-second wait
        PageFactory.initElements(rdriver, this);
    }
    
    // Locators - Finders 
    @FindBy(id = "user-name")
    @CacheLookup
	static
    WebElement CustomerEmail;
    
    @FindBy(id = "password")
    @CacheLookup
	static
    WebElement CustomerPassword;
    
    @FindBy(id = "login-button")
    @CacheLookup
	static
    WebElement btnLogin;
    
    @FindBy(css = "[data-test='error']")
    @CacheLookup
    WebElement errorMessage;
    
    
    
    // Actions - Methods
    public void getLoginPage() {
        // Navigate to SauceDemo login page
        ldriver.get("https://www.saucedemo.com/");

        // Wait until the username field is visible to ensure page is loaded
        wait.until(ExpectedConditions.visibilityOf(CustomerEmail));
    }
    
    
    public void setUserName(String userName) {
        wait.until(ExpectedConditions.visibilityOf(CustomerEmail)); // Wait for visibility
        CustomerEmail.clear();
        CustomerEmail.sendKeys(userName);
    }
    
    public void setPassWord(String pwd) {
        wait.until(ExpectedConditions.visibilityOf(CustomerPassword)); // Wait for visibility
        CustomerPassword.clear();
        CustomerPassword.sendKeys(pwd);
    }
    
    public void clickSubmit() {
        wait.until(ExpectedConditions.elementToBeClickable(btnLogin)); // Wait for clickability
        btnLogin.click();
    }
    
    public String gerErrorMessage() {
       wait.until(ExpectedConditions.visibilityOf(errorMessage)); // Wait for clickability
      return  errorMessage.getText();
    }
    
}
