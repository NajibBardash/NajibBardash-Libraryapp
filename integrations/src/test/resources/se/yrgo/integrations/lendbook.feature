Feature: Lending books
  As an administrator
  I want to be able to lend books to users
  And as a user
  I want to view my current loans

  Scenario: Administrator lends a book to a user
    Given an administrator is logged in
    And a copy of book 12 is available
    When the administrator lends book 12 to user 2
    Then user 2 should have book 12 on loan

  Scenario: User can view their current loans
    Given user 2 is logged in
    And user 2 has book 4 on loan
    When the user views their loans
    Then the loans list should include book 4