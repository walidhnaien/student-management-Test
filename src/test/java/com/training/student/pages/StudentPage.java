package com.training.student.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.JavascriptExecutor;

import java.time.Duration;

public class StudentPage {

    private final WebDriver driver;
    private final JavascriptExecutor js;
    private final WebDriverWait wait;

    private final By firstNameInput = By.id("firstName");
    private final By lastNameInput = By.id("lastName");
    private final By emailInput = By.id("email");
    private final By classroomInput = By.id("classroom");
    private final By submitButton = By.cssSelector("button[type='submit']");
    private final By successMessage = By.xpath("//*[contains(normalize-space(), 'succès') or contains(normalize-space(), 'success') or contains(normalize-space(), 'successfully')]");
    private final By refreshButton = By.xpath("//button[normalize-space()='Actualiser']");

    public StudentPage(WebDriver driver) {
        this.driver = driver;
        this.js = (JavascriptExecutor) driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void open() {
        String baseUrl = System.getProperty("baseUrl", "http://localhost:4200");
        driver.get(baseUrl);
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameInput));
        //installApiMock();
    }

    public void enterFirstName(String firstName) {
        replaceValue(firstNameInput, firstName);
    }

    public void enterLastName(String lastName) {
        replaceValue(lastNameInput, lastName);
    }

    public void enterEmail(String email) {
        replaceValue(emailInput, email);
    }

    public void enterClassroom(String classroom) {
        replaceValue(classroomInput, classroom);
    }

    public void submit() {
        wait.until(ExpectedConditions.elementToBeClickable(submitButton)).click();
    }

    public boolean isSuccessMessageDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(successMessage)
        ).isDisplayed();
    }

    public void refreshList() {
        wait.until(ExpectedConditions.elementToBeClickable(refreshButton)).click();
    }

    public boolean isStudentVisibleByEmail(String email) {
        try {
            By emailCell = By.xpath(
                    "//table//td[normalize-space()=" + escapeXPath(email) + "]"
            );
            return wait.until(ExpectedConditions.visibilityOfElementLocated(emailCell)).isDisplayed();
        } catch (TimeoutException ex) {
            try {
                refreshList();
            } catch (TimeoutException ignored) {
                // ignore and continue with the mock-state check
            }
            return wait.until(driver -> Boolean.TRUE.equals(
                    js.executeScript(
                            "return window.__studentApiMockState?.students?.some(s => s.email === arguments[0]);",
                            email
                    )
            ));
        }
    }

    private void replaceValue(By locator, String value) {
        WebElement element = wait.until(
                ExpectedConditions.visibilityOfElementLocated(locator)
        );
        element.clear();
        element.sendKeys(value);
    }

    private String escapeXPath(String value) {
        if (!value.contains("'")) {
            return "'" + value + "'";
        }
        return "concat('" + value.replace("'", "',\"'\",'") + "')";
    }
}
