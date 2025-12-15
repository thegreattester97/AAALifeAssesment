package test.pageObjects;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;


public class ProductsPage {

    WebDriver ldriver;
    WebDriverWait wait;

    // Constructor
    public ProductsPage(WebDriver rdriver) {
        ldriver = rdriver;
        wait = new WebDriverWait(ldriver, Duration.ofSeconds(10));
        PageFactory.initElements(rdriver, this);
    }

    // Locators
    
    // Page Header
    @FindBy(css = "span.title")
    WebElement pageHeader;
    
    // List of Producst on page 
    @FindBy(css = "div.inventory_item")
    List<WebElement> productList;
    
    // Add to cart button 
    @FindBy(css = "button.btn_inventory")
    List<WebElement> addToCartButtons;
    
    // Cart Link
    @FindBy(css = "a.shopping_cart_link")
    WebElement cartLink;
    
    // Cart Badge 
    @FindBy(css = "span.shopping_cart_badge")
    List<WebElement> cartBadge;    
    
    // Cart Items
    @FindBy(css = "div.inventory_item_name")
    List<WebElement> cartItems;

    // Actions
    public void open() {
        ldriver.get("https://www.saucedemo.com/inventory.html");
    }

    public boolean isPageLoaded() {
        return wait.until(ExpectedConditions.visibilityOf(pageHeader)).isDisplayed();
    }

    public int getProductCount() {
        return wait.until(ExpectedConditions.visibilityOfAllElements(productList)).size();
    }

    public void addFirstItemToCart() {
        WebElement firstButton = wait.until(ExpectedConditions.elementToBeClickable(addToCartButtons.get(0)));
        try {
            firstButton.click();
        } catch (ElementClickInterceptedException e) {
            // fallback click if overlay or animation blocks normal click
            ((JavascriptExecutor) ldriver).executeScript("arguments[0].click();", firstButton);
        }
        wait.until(driver -> getCartCountSafely() > 0);
    
    }
    public void clickCart() {
        wait.until(ExpectedConditions.elementToBeClickable(cartLink)).click();
    }

    public int getCartCountSafely() {
        try {
            if (cartBadge.isEmpty()) {
                return 0; // No badge present yet
            }
            WebElement badge = wait.until(ExpectedConditions.visibilityOf(cartBadge.get(0)));
            return Integer.parseInt(badge.getText());
        } catch (Exception e) {
            return 0; // fallback if badge missing
        }
    }

    public String getFirstProductName() {
        return wait.until(ExpectedConditions.visibilityOf(productList.get(0))).getText();
    }

    public String getCartItemName() {
        wait.until(ExpectedConditions.visibilityOfAllElements(cartItems));
        return cartItems.get(0).getText();
    }
}