# language: en
@ui @creation
Feature: Student creation

  As a user of the Student Management application
  I want to create a student
  So that I can register and find them in the list

  Scenario: Create a student with valid information
    Given the user opens the Student Management application
    When he fills in the first name "Weil"
    Then a success creation message is displayed

  Scenario Outline: Register a student with valid data
    Given the user is on the creation form
    When he enters the first name "<firstName>"
    And he enters the last name "<lastName>"
    And he enters the email "<email>"
    And he enters the classroom "<classroom>"
    Then a student is created successfully

    Examples:
      | firstName | lastName | email                  | classroom     |
      | Weil      | Hnaien   | weil.hnaien@test.com  | QA Automation |
      | Alice     | Dupont   | alice.dupont@test.com | DevOps |
