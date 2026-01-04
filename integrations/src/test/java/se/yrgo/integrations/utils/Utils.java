package se.yrgo.integrations.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import se.yrgo.integrations.pages.StartPage;

import java.time.Duration;

public final class Utils {
    private Utils() {}

    public static StartPage openStartPage(WebDriver driver) {
        final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        driver.manage().window().maximize();
        driver.get("http://frontend");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("hero")));
        return new StartPage(driver);
    }

    public static WebElement find(WebDriver driver, By locator) {
        final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return driver.findElement(locator);
    }
}
