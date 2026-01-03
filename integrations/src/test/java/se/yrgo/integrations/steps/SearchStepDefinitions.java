package se.yrgo.integrations.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;

import se.yrgo.integrations.pages.SearchPage;
import se.yrgo.integrations.viewmodel.BookView;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SearchStepDefinitions {

    private SearchPage searchPage() {
        SearchPage page = GeneralStepDefinitions.getSearchPage();
        if (page == null) {
            throw new IllegalStateException("SearchPage is not set. Did you navigate to the search page?");
        }
        return page;
    }
    @Then("they can see the search form")
    public void they_can_see_the_search_form() {
        assertThat(searchPage().isSearchFormVisible()).isTrue();
    }

    @When("the user searches for books by author {string}")
    public void the_user_searches_for_books_by_author(String author) {
        searchPage().searchByAuthor(author);
    }

    @Then("the following books are shown:")
    public void the_following_books_by_are_shown(DataTable table) {
        List<String> expectedTitles = table.asList();
        List<BookView> foundBooks = searchPage().getMatchingBooks();

        int found = 0;
        for (String title : expectedTitles) {
            for (BookView book : foundBooks) {
                if (book.getTitle().equals(title)) {
                    found++;
                    break;
                }
            }
        }

        assertThat(found).isEqualTo(expectedTitles.size());
    }

    @When("the user submits the search without any parameters")
    public void the_user_submits_the_search_without_any_parameters() {
        searchPage().searchWithEmptyParameters();
    }

    @Then("no books are shown")
    public void no_books_are_shown() {
        assertThat(searchPage().getMatchingBooks()).isEmpty();
    }

    @Then("an error message is displayed")
    public void an_error_message_is_displayed() {
        assertThat(searchPage().isErrorMessageVisible()).isTrue();
    }
}
