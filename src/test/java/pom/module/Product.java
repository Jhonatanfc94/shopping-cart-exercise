package pom.module;

import auxiliary.config.GeneralMethods;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Product extends GeneralMethods{
    WebDriver driver;
    WebDriverWait wait;
    AjaxElementLocatorFactory ajaxElementLocatorFactory;

    @FindBy(xpath = "//button[@id='button-cart']")
    public WebElement addToCartButton;
    @FindBy(xpath = "//div[contains(text(),'Success: You have added ')]//a[contains(text(),'shopping cart')]/parent::div")
    public WebElement shoppingCartAlert;

    public Product(WebDriver driver){
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        ajaxElementLocatorFactory = new AjaxElementLocatorFactory(this.driver,10);
        PageFactory.initElements(ajaxElementLocatorFactory,this);
    }
}