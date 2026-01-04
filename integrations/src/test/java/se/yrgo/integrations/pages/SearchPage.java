package se.yrgo.integrations.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import se.yrgo.integrations.utils.Utils;
import se.yrgo.integrations.viewmodel.BookView;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class SearchPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By formInput = By.cssSelector("form input[type='text']");
    private final By submit = By.cssSelector("form input[type='submit']");

    private final By authorInput = By.cssSelector("input[placeholder='Author']");
    private final By bookRows = By.cssSelector("table tbody tr");
    private final By noBooksFoundMessage = By.cssSelector(".errors div");


    public SearchPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        wait.until(visibilityOfElementLocated(By.cssSelector("form")));
    }

    public boolean isSearchFormVisible() {
        wait.until(visibilityOfElementLocated(formInput));
        wait.until(ExpectedConditions.elementToBeClickable(submit));
        return true;
    }

    public boolean isErrorMessageVisible() {
        return !driver.findElements(noBooksFoundMessage).isEmpty()
                && driver.findElement(noBooksFoundMessage).isDisplayed()
                && driver.findElement(noBooksFoundMessage).getText().contains("No books found");
    }

    private void waitForResultsOrError() {
        wait.until(ExpectedConditions.or(
                ExpectedConditions.presenceOfAllElementsLocatedBy(bookRows),
                ExpectedConditions.visibilityOfElementLocated(noBooksFoundMessage)
        ));
    }

    public void searchByAuthor(String author) {
        isSearchFormVisible();

        var input = wait.until(visibilityOfElementLocated(authorInput));
        input.clear();
        input.sendKeys(author);

        Utils.find(driver, submit).click();
        waitForResultsOrError();
    }

    public void searchWithEmptyParameters() {
        isSearchFormVisible();

        Utils.find(driver, submit).click();
        waitForResultsOrError();
    }

    public List<BookView> getMatchingBooks() {
        List<BookView> matchingBooks = new ArrayList<>();

        if (isErrorMessageVisible()) {
            return matchingBooks;
        }

        var rows = driver.findElements(bookRows);

        for (WebElement row : rows) {
            var bookData = row.findElements(By.tagName("td"));

            String title = bookData.get(0).getText();
            String author = bookData.get(1).getText();
            String isbn = bookData.get(2).getText();
            String category = bookData.get(3).getText();

            matchingBooks.add(new BookView(title, author, isbn, category));
        }
        return matchingBooks;
    }
}
