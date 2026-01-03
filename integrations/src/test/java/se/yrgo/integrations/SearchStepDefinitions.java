package se.yrgo.integrations;

import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SearchStepDefinitions {
    final WebDriver driver = GeneralStepDefinitions.getDriver();

    @When("the user navigates to the book search.")
    public void the_user_navigates_to_the_book_search() {
        final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement searchLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Search")));
        searchLink.click();
    }

    @Then("they can see the search form.")
    public void they_can_see_the_search_form() {
        final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement searchForm = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("form")));

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("form input[type='text']"))
        );

        wait.until(
                ExpectedConditions.elementToBeClickable(By.cssSelector("form input[type='submit']"))
        );
    }
}
