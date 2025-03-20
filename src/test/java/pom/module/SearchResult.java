package pom.module;

import auxiliary.config.GeneralMethods;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SearchResult extends GeneralMethods{
    WebDriver driver;
    WebDriverWait wait;
    AjaxElementLocatorFactory ajaxElementLocatorFactory;

    public SearchResult(WebDriver driver){
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        ajaxElementLocatorFactory = new AjaxElementLocatorFactory(this.driver,10);
        PageFactory.initElements(ajaxElementLocatorFactory,this);
    }

    public void clickProduct(String product){
        findXPathElement("//h2[text()='Search']/following-sibling::div//a[text()='"+product+"']",driver,wait).click();
    }
}