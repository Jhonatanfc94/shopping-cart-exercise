package stepdefinition;

import auxiliary.config.DriverManager;
import auxiliary.config.GeneralMethods;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pom.module.Menu;
import pom.module.Product;
import pom.module.SearchResult;
import pom.module.ShoppingCart;

public class ShoppingCartSteps extends GeneralMethods {
    public Menu menu;
    public SearchResult searchResult;
    public ShoppingCart shoppingCart;
    public Product product;

    public ShoppingCartSteps(){
        menu = new Menu(DriverManager.getDriver());
        searchResult = new SearchResult(DriverManager.getDriver());
        shoppingCart = new ShoppingCart(DriverManager.getDriver());
        product = new Product(DriverManager.getDriver());
    }

    @Given("I am on the home page")
    public void iAmOnTheHomePage() {
        loadUrl(DriverManager.getDriver(),"http://opencart.abstracta.us/");
    }

    @When("I search for {string}")
    public void iSearchFor(String productName) {
        menu.searchProduct(productName);
    }

    @When("I click {string} from search results")
    public void iSelectFromSearchResults(String productName) {
        searchResult.clickProduct(productName);
    }

    @When("I add the product to the shopping cart")
    public void iAddTheProductToTheShoppingCart() {
        product.addToCartButton.click();
    }

    @When("I click {string} from top menu")
    public void iClickFromTopNavMenu(String menuOption) {
        menu.clickOptionTopMenu(menuOption);
    }

    @When("I remove {string} from the cart")
    public void iRemoveFromTheCart(String productName) {
        shoppingCart.removeProductFromCart(productName);
        waitSeconds(2);
    }

    @Then("I should see the search results page")
    public void iShouldSeeTheSearchResults() {
        Assert.assertTrue(DriverManager.getDriver().getCurrentUrl().contains("route=product/search"),"Search results should be displayed");
    }

    @Then("I should see {string} in the shopping cart")
    public void iShouldSeeInTheShoppingCart(String productName) {
        boolean isProductInCart = shoppingCart.isProductNameInCart(productName);
        Assert.assertTrue(isProductInCart);
    }

    @Then("I should see {string} alert")
    public void iShouldSeeMessage(String expectedMessage) {
        String actualMessage = product.shoppingCartAlert.getText();
        Assert.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Then("I should not see {string} in the shopping cart")
    public void iShouldNotSeeInTheShoppingCart(String productName) {
        boolean isProductInCart = shoppingCart.isProductNameInCart(productName);
        Assert.assertFalse(isProductInCart);
    }
}