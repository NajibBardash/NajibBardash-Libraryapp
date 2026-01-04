package se.yrgo.integrations.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import se.yrgo.integrations.utils.CustomConditions;
import se.yrgo.integrations.utils.Utils;

import java.time.Duration;

public class LoginPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By usernameInput = By.cssSelector("input[placeholder='Username']");
    private final By passwordInput = By.cssSelector("input[placeholder='Password']");
    private final By loginButton = By.cssSelector("input[type='submit'][value='Login']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        wait.until(ExpectedConditions.visibilityOfElementLocated(loginButton));
    }

    public void login(String role) {
        String username = "";

        if (role.equals("admin")) {
            username = "test";
        }

        else if (role.equals("user")) {
            username = "test2";
        }
        else {
            throw new IllegalArgumentException("Invalid role: " + role);
        }

        var usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(usernameInput));
        usernameField.clear();
        usernameField.sendKeys(username);

        var passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInput));
        passwordField.clear();
        passwordField.sendKeys("yrgoP4ssword");

        var submitLogin = Utils.find(driver, loginButton);
        wait.until(CustomConditions.elementHasBeenClicked(submitLogin));
    }

    public LendingPage navigateToLendingPage() {
        final var adminLink = Utils.find(driver, By.linkText("Administration"));
        wait.until(CustomConditions.elementHasBeenClicked(adminLink));

        final var lendingLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Handle loans")));
        wait.until(CustomConditions.elementHasBeenClicked(lendingLink));

        wait.until(ExpectedConditions.urlContains("/admin/loans"));

        return new LendingPage(driver);
    }

    public LoansPage navigateToLoansPage() {
        final var loansLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("My Books")));
        wait.until(CustomConditions.elementHasBeenClicked(loansLink));

        wait.until(ExpectedConditions.urlContains("/user/loans"));

        return new LoansPage(driver);
    }
}
