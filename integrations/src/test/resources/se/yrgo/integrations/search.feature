Feature: Searching for books
  As a user
  I want to be able to search for available books
  so I know what I can loan

  Scenario: Getting to the search page
    Given the user is on the start page
    When the user navigates to the book search
    Then they can see the search form

  Scenario: Searching for books by author
    Given the user is on the search page
    When the user searches for books by author "Dean Koontz"
    Then the following books are shown:
      | Prodigal son            |
      | The eyes of darkness    |

  Scenario: Searching without any parameters shows no results
    Given the user is on the search page
    When the user submits the search without any parameters
    Then no books are shown
    And an error message is displayed
