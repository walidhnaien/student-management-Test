# language: en
@ui @display
Feature: Student display

  As a user of the Student Management application
  I want to see registered students in the student list
  So that I can confirm a student is available

  Scenario: Display a newly registered student in the list
    Given the user is on the creation form
    When he enters the first name "Display"
    And he enters the last name "Student"
    And he fills in a unique email address
    And he fills in the classroom "QA Automation"
    And he validates student creation
    Then a success creation message is displayed
    And the new student appears in the list