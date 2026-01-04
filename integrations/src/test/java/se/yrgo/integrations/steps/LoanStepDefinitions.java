package se.yrgo.integrations.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import se.yrgo.integrations.pages.LendingPage;
import se.yrgo.integrations.pages.LoansPage;
import se.yrgo.integrations.pages.LoginPage;
import se.yrgo.integrations.pages.StartPage;
import se.yrgo.integrations.utils.BackendClient;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;

public class LoanStepDefinitions {
    private final StartPage startPage = GeneralStepDefinitions.getStartPage();
    private final BackendClient client = new BackendClient();

    private LoginPage loginPage;
    private LoansPage loansPage;


    @Given("an administrator is logged in")
    public void an_administrator_is_logged_in() {
        loginPage = startPage.navigateToLoginPage();
        loginPage.login("admin");
    }

    @Given("a copy of book {int} is available")
    public void and_a_copy_of_book_is_available(Integer int1) {
        // Book is available from the setup in the migrations;
    }

    @When("the administrator lends book {int} to user {int}")
    public void the_administrator_lends_book_to_user(Integer bookCopyId, Integer userId) {
        LendingPage lendingPage = loginPage.navigateToLendingPage();
        lendingPage.lendBookToUser(bookCopyId, userId);
    }

    @Then("user {int} should have book {int} on loan")
    public void user_should_have_book_on_loan(Integer userId, Integer bookCopyId) {

        String bookLoansAsJson = null;
        try {
            bookLoansAsJson = client.getBooksOnLoan();
            assertThat(client.containsBookCopy(bookLoansAsJson, userId, bookCopyId)).isTrue();
        } catch (IOException | InterruptedException e) {
            fail("Could not fetch loans from backend", e);
        }
    }

    @Given("user {int} is logged in")
    public void a_user_is_logged_in(Integer userId) {
        loginPage = startPage.navigateToLoginPage();
        loginPage.login("user");    }

    @Given("user {int} has book {int} on loan")
    public void the_user_has_book_on_loan(Integer userId, Integer bookCopyId) {
        // User has the book from the setup in the migrations;
    }

    @When("the user views their loans")
    public void the_user_views_their_loans() {
        loansPage = loginPage.navigateToLoansPage();
    }

    @Then("the loans list should include book {int}")
    public void the_loans_list_should_include_book(Integer bookCopyId) {
        assertThat(loansPage.containsBookAsLoaned(bookCopyId)).isTrue();
    }
}
