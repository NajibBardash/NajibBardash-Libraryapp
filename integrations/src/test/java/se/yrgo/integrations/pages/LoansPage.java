package se.yrgo.integrations.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoansPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By loansTableRows = By.cssSelector("table tbody tr");

    public LoansPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        wait.until(ExpectedConditions.urlContains("/user/loans"));
    }

    public boolean containsBookAsLoaned(Integer bookCopyId) {
        var rows = driver.findElements(loansTableRows);

        for (WebElement row : rows) {
            var bookData = row.findElements(By.tagName("td"));

            if (bookData.get(0).getText().equals(String.valueOf(bookCopyId))) {
                return true;
            }
        }
        return false;
    }
}
