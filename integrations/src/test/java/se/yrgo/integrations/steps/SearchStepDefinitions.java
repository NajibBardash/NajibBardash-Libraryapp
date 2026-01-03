package se.yrgo.integrations.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;

import se.yrgo.integrations.pages.SearchPage;
import se.yrgo.integrations.viewmodel.BookView;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SearchStepDefinitions {
    final SearchPage searchPage = GeneralStepDefinitions.getSearchPage();

    @Then("they can see the search form")
    public void they_can_see_the_search_form() {
        assertThat(searchPage.isSearchFormVisible()).isTrue();
    }

    @When("the user searches for books by author {string}")
    public void the_user_searches_for_books_by_author(String author) {
        searchPage.searchByAuthor(author);
    }

    @Then("the following books are shown:")
    public void the_following_books_by_are_shown(DataTable table) {
        List<String> expectedTitles = table.asList();
        List<BookView> foundBooks = searchPage.getMatchingBooks();

        int found = 0;
        for (String title : expectedTitles) {
            for (BookView book : foundBooks) {
                if (book.getTitle().equals(title)) {
                    found++;
                }
            }
        }

        assertThat(found).isEqualTo(expectedTitles.size());
    }
}
