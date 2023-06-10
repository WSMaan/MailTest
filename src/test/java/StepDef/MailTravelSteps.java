import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class MailTravelSteps {

    WebDriver driver;
    WebDriverWait wait; // Declare a WebDriverWait object

    @Given("I open a browser and navigate to {string}")
    public void i_open_a_browser_and_navigate_to(String url) {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get(url);
        wait = new WebDriverWait(driver, 10); // Initialize the WebDriverWait object with a timeout of 10 seconds
    }

    @When("I accept all cookies")
    public void accept_cookies() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        driver.manage().window().maximize();
        WebElement accept = driver.findElement(By.xpath("//button[@id=\"onetrust-accept-btn-handler\"]"));
        js.executeScript("arguments[0].click();", accept);
        // Wait for the cookie banner to disappear
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("cookie-banner")));
    }

    @When("I enter {string} in the search bar and click on the search button")
    public void i_enter_in_the_search_bar_and_click_on_the_search_button(String term) {

        WebElement search = driver.findElement(By.xpath("//input[@id='searchtext_freetext_search_form']"));
        search.click();
        search.sendKeys(term);
        WebElement searchButton = driver.findElement(By.id("searchbutton_freetext_search_form"));
        searchButton.click();
        // Wait for the results page to load
        wait.until(ExpectedConditions.urlContains("search"));
    }

    @When("I select holiday package")
    public void click_on_package() {
        WebElement firstResult = driver.findElement(By.xpath("//div[@id='iterator_2_product_custom_product-content']"));
        firstResult.click();
        // Wait for the package page to load
        wait.until(ExpectedConditions.titleContains("India"));
    }

    @When("I click on the book online button")
    public void click_on_book_online_button() {
        WebElement bookOnlineButton = driver.findElement(By.cssSelector("#book-button-header > #book-d50c5ad7fbd04a98d656d673711d524a > .nbf_button"));
        bookOnlineButton.click();
    }

    @Then("I should see an error message {string}")
    public void error_message(String message) {
        WebElement errorMessage = driver.findElement(By.xpath("//div[@class = \"fielderror\"]"));
        boolean isMessageDisplayed = errorMessage.isDisplayed();
        Assert.assertTrue(isMessageDisplayed, "Error message not displayed");
        Assert.assertEquals(errorMessage.getText(), message, "Error message not matched");
    }

    @When("I select two passenger from the dropdown")
    public void select_passengers() {
        WebElement passengers = driver.findElement(By.id("passenger-dropdown-numpassengers"));
        passengers.click();
        JavascriptExecutor js1 = (JavascriptExecutor) driver;
        js1.executeScript("arguments[0].scrollIntoView(true);", passengers);
        Select dropDown = new Select(passengers);
        dropDown.selectByIndex(1);
    }

    @When("I select a departure date from the calendar")
    public void select_departure_date() {
        WebElement departureDate = driver.findElement(By.id("calbox-d50c5ad7fbd04a98d656d673711d524a-1"));
        new Actions(driver).moveToElement(departureDate).click().build().perform();
    }

    @Then("I should see the tour price")
    public void tour_price() {
        WebElement tourPrice = driver.findElement(By.xpath("//div[@ id=\"tour-price-wrap\"]"));
        boolean isPriceDisplayed = tourPrice.isDisplayed();
        Assert.assertTrue(isPriceDisplayed, "Tour price displayed");
    }
}
