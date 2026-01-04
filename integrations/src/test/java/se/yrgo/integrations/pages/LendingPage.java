package se.yrgo.integrations.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import se.yrgo.integrations.utils.CustomConditions;
import se.yrgo.integrations.utils.Utils;

import java.time.Duration;

public class LendingPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By bookIdInput = By.cssSelector("input[placeholder='Book ID']");
    private final By userIdInput = By.cssSelector("input[placeholder='User ID']");
    private final By lendButton = By.cssSelector("input[type='submit'][value='Lend book']");

    private final By acceptLendButton = By.xpath("//button[normalize-space()='Ok']");

    public LendingPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        wait.until(ExpectedConditions.urlContains("/admin/loans"));
    }

    public void lendBookToUser(int bookCopyId, int userId) {
        var bookInput = wait.until(ExpectedConditions.visibilityOfElementLocated(bookIdInput));
        bookInput.clear();
        bookInput.sendKeys(String.valueOf(bookCopyId));

        var userInput = wait.until(ExpectedConditions.visibilityOfElementLocated(userIdInput));
        userInput.clear();
        userInput.sendKeys(String.valueOf(userId));

        var submitLend = Utils.find(driver, lendButton);
        wait.until(CustomConditions.elementHasBeenClicked(submitLend));

        var acceptLend = wait.until(ExpectedConditions.visibilityOfElementLocated(acceptLendButton));
        wait.until(CustomConditions.elementHasBeenClicked(acceptLend));
    }
}
