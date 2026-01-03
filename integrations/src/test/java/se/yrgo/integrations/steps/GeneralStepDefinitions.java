package se.yrgo.integrations.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;

import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import se.yrgo.integrations.pages.SearchPage;
import se.yrgo.integrations.pages.StartPage;
import se.yrgo.integrations.utils.Utils;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.fail;

public class GeneralStepDefinitions {
    private static WebDriver driver;
    private static StartPage startPage;
    private static SearchPage searchPage;

    @Before
    public void setup() {
        try {
            ChromeOptions options = new ChromeOptions();
            driver = new RemoteWebDriver(new URL("http://localhost:4444"),
                    options, false);
            startPage = Utils.openStartPage(driver);
        } catch (MalformedURLException e) {
            fail(e);
        }
    }

    @After
    public void shutdown() {
        if (driver != null) {
            driver.quit();
        }
        driver = null;
        startPage = null;
        searchPage = null;
    }

    public static WebDriver getDriver() {
        return driver;
    }

    public static StartPage getStartPage() {
        return startPage;
    }

    public static SearchPage getSearchPage() { return searchPage; }

    public static void setSearchPage(SearchPage page) {
        searchPage = page;
    }

    @Given("the user is on the start page")
    public void the_user_is_on_the_start_page() {
        if (!startPage.getTitle().equals("The Library")) {
            throw new IllegalStateException("The user is not on the start page.");
        }
    }

    @Given("the user is on the search page")
    public void the_user_is_on_the_search_page() {
        searchPage = startPage.navigateToSearchPage();
    }

    @When("the user navigates to the book search")
    public void the_user_navigates_to_the_book_search() {
        setSearchPage(startPage.navigateToSearchPage());
    }
}
