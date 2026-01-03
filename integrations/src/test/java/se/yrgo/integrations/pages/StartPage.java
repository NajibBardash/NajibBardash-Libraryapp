package se.yrgo.integrations.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import se.yrgo.integrations.utils.CustomConditions;
import se.yrgo.integrations.utils.Utils;

import java.time.Duration;

public class StartPage {
    private final WebDriver driver;

    public StartPage(WebDriver driver) {
        this.driver = driver;

        if (!driver.getTitle().equals("The Library")) {
            throw new IllegalStateException("Not on the start page");
        }
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public SearchPage navigateToSearchPage() {
        final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        final WebElement searchLink = Utils.find(driver, By.linkText("FIND A BOOK"));
        wait.until(CustomConditions.elementHasBeenClicked(searchLink));

        wait.until(ExpectedConditions.urlContains("/search"));

        return new SearchPage(driver);
    }
}
