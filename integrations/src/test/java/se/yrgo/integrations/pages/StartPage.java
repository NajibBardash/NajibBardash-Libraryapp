package se.yrgo.integrations.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import se.yrgo.integrations.utils.CustomConditions;
import se.yrgo.integrations.utils.Utils;

import java.time.Duration;

public class StartPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public StartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        if (!driver.getTitle().equals("The Library")) {
            throw new IllegalStateException("Not on the start page");
        }
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public LoginPage navigateToLoginPage() {
        final var loginLink = Utils.find(driver, By.linkText("LOGIN"));
        wait.until(CustomConditions.elementHasBeenClicked(loginLink));

        wait.until(ExpectedConditions.urlContains("/login"));

        return new LoginPage(driver);
    }

    public SearchPage navigateToSearchPage() {
        final var searchLink = Utils.find(driver, By.linkText("FIND A BOOK"));
        wait.until(CustomConditions.elementHasBeenClicked(searchLink));

        wait.until(ExpectedConditions.urlContains("/search"));

        return new SearchPage(driver);
    }
}
