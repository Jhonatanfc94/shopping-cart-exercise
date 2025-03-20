package pom.module;

import auxiliary.config.GeneralMethods;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ShoppingCart extends GeneralMethods{
    WebDriver driver;
    WebDriverWait wait;
    AjaxElementLocatorFactory ajaxElementLocatorFactory;

    @FindBy(xpath = "(//span[text()='Get in Touch'])[1]")
    public WebElement webElement;

    public ShoppingCart(WebDriver driver){
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        ajaxElementLocatorFactory = new AjaxElementLocatorFactory(this.driver,10);
        PageFactory.initElements(ajaxElementLocatorFactory,this);
    }

    public boolean isProductNameInCart(String productName){
        return xpathExists("//*[@class='table-responsive']//table//tr/td[2]//a[text()='"+productName+"']",wait);
    }

    public void removeProductFromCart(String productName){
        findXPathElement("//*[@class='table-responsive']//table//tr/td[2]//a[text()='iPhone']/ancestor::tr//*[@data-original-title=\"Remove\"]",driver,wait).click();
    }
}