package se.yrgo.integrations.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import se.yrgo.integrations.utils.CustomConditions;
import se.yrgo.integrations.utils.Utils;
import se.yrgo.integrations.viewmodel.BookView;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class SearchPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By form = By.cssSelector("form");
    private final By formInput = By.cssSelector("form input[type='text']");
    private final By submit = By.cssSelector("form input[type='submit']");

    private final By authorInput = By.cssSelector("input[placeholder='Author']");


    public SearchPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        wait.until(visibilityOfElementLocated(form));
    }

    public boolean isSearchFormVisible() {
        wait.until(visibilityOfElementLocated(formInput));
        wait.until(ExpectedConditions.elementToBeClickable(submit));
        return true;
    }

    public void searchByAuthor(String author) {
        if (isSearchFormVisible()) {
            WebElement input = wait.until(visibilityOfElementLocated(authorInput));
            input.clear();
            input.sendKeys(author);

            final WebElement submitButton = Utils.find(driver, submit);
            wait.until(CustomConditions.elementHasBeenClicked(submitButton));
        }
    }

    public List<BookView> getMatchingBooks() {
        List<BookView> matchingBooks = new ArrayList<>();

        List<WebElement> bookRows = driver.findElements(By.cssSelector("table tbody tr"));

        for (WebElement row : bookRows) {
            List<WebElement> bookData = row.findElements(By.tagName("td"));

            String title = bookData.get(0).getText();
            String author = bookData.get(1).getText();
            String isbn = bookData.get(2).getText();
            String category = bookData.get(3).getText();

            matchingBooks.add(new BookView(title, author, isbn, category));
        }
        return matchingBooks;
    }
}
