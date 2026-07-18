package com.training.student.support;

import org.openqa.selenium.WebDriver;

public final class TestContext {

    private WebDriver driver;
    private String generatedEmail;

    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    public String getGeneratedEmail() {
        return generatedEmail;
    }

    public void setGeneratedEmail(String generatedEmail) {
        this.generatedEmail = generatedEmail;
    }
}
