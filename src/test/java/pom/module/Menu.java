package pom.module;

import auxiliary.config.GeneralMethods;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Menu extends GeneralMethods{
    WebDriver driver;
    WebDriverWait wait;
    AjaxElementLocatorFactory ajaxElementLocatorFactory;

    @FindBy(xpath = "//input[@name='search']")
    public WebElement searchInput;
    @FindBy(xpath = "//input[@name='search']/..//button")
    public WebElement searchButton;

    public Menu(WebDriver driver){
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        ajaxElementLocatorFactory = new AjaxElementLocatorFactory(this.driver,10);
        PageFactory.initElements(ajaxElementLocatorFactory,this);
    }

    public void searchProduct(String product){
        sendKeys(searchInput,product, driver, wait);
        searchButton.click();
    }

    public void clickOptionTopMenu(String option){
        findXPathElement("//a//span[text()='"+option+"']",driver,wait).click();
    }
}