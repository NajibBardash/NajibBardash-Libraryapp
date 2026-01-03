package se.yrgo.integrations.steps;

import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;

import se.yrgo.integrations.pages.SearchPage;
import se.yrgo.integrations.pages.StartPage;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SearchStepDefinitions {
    final WebDriver driver = GeneralStepDefinitions.getDriver();
    final StartPage startPage = GeneralStepDefinitions.getStartPage();
    private SearchPage searchPage;

    @When("the user navigates to the book search.")
    public void the_user_navigates_to_the_book_search() {
        searchPage = startPage.navigateToSearchPage();
    }

    @Then("they can see the search form.")
    public void they_can_see_the_search_form() {
        assertThat(searchPage.isSearchFormVisible()).isTrue();
    }
}
