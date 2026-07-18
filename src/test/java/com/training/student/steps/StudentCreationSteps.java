package com.training.student.steps;

import com.training.student.pages.StudentPage;
import com.training.student.support.TestContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class StudentCreationSteps {

    private final TestContext context;
    private StudentPage studentPage;

    public StudentCreationSteps(TestContext context) {
        this.context = context;
    }

    @Given("the user opens the Student Management application")
    public void openStudentManagement() {
        initializePage();
        studentPage.open();
    }

    @Given("the user is on the creation form")
    public void openCreationForm() {
        initializePage();
        studentPage.open();
    }

    @When("he fills in the first name {string}")
    public void enterFirstName(String firstName) {
        initializePage();
        studentPage.enterFirstName(firstName);
    }

    @When("he enters the first name {string}")
    public void enterFirstNameOutline(String firstName) {
        initializePage();
        studentPage.enterFirstName(firstName);
    }

    @And("he fills in the last name {string}")
    public void enterLastName(String lastName) {
        initializePage();
        studentPage.enterLastName(lastName);
    }

    @And("he enters the last name {string}")
    public void enterLastNameOutline(String lastName) {
        initializePage();
        studentPage.enterLastName(lastName);
    }

    @And("he fills in a unique email address")
    public void enterUniqueEmail() {
        initializePage();
        String email = "student." + Instant.now().toEpochMilli() + "@test.local";
        context.setGeneratedEmail(email);
        studentPage.enterEmail(email);
    }

    @And("he enters the email {string}")
    public void enterEmailOutline(String email) {
        initializePage();
        context.setGeneratedEmail(email);
        studentPage.enterEmail(email);
    }

    @And("he fills in the classroom {string}")
    public void enterClassroom(String classroom) {
        initializePage();
        studentPage.enterClassroom(classroom);
    }

    @And("he enters the classroom {string}")
    public void enterClassroomOutline(String classroom) {
        initializePage();
        studentPage.enterClassroom(classroom);
    }

    @And("he validates student creation")
    public void submitStudentCreation() {
        initializePage();
        studentPage.submit();
    }

    @Then("a success creation message is displayed")
    public void verifySuccessMessage() {
        initializePage();
        assertTrue(
                studentPage.isSuccessMessageDisplayed(),
                "The success message is not displayed."
        );
    }

    @Then("a student is created successfully")
    public void verifySuccessMessageOutline() {
        initializePage();
        assertTrue(
                studentPage.isSuccessMessageDisplayed(),
                "The success message is not displayed."
        );
    }

    @And("the new student appears in the list")
    public void verifyStudentInList() {
        initializePage();
        studentPage.refreshList();
        assertTrue(
                studentPage.isStudentVisibleByEmail(context.getGeneratedEmail()),
                "The created student does not appear in the list."
        );
    }

    private void initializePage() {
        if (studentPage == null) {
            studentPage = new StudentPage(context.getDriver());
        }
    }
}
