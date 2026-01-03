package se.yrgo.integrations.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SearchPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By form = By.cssSelector("form");
    private final By formInput = By.cssSelector("form input[type='text']");
    private final By submit = By.cssSelector("form input[type='submit']");

    public SearchPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        wait.until(ExpectedConditions.visibilityOfElementLocated(form));
    }

    public boolean isSearchFormVisible() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(formInput));
        wait.until(ExpectedConditions.elementToBeClickable(submit));
        return true;
    }
}
