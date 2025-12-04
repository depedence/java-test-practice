package com.example;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.assertj.core.api.Assertions.assertThat;

public class HomePageTest extends UiTestBase {

    @Test
    void homePageLoadSuccessfully() {
        driver.get(getBaseUrl());
        assertThat(driver.getTitle()).contains("Test API Interface");

        WebElement header = driver.findElement(By.tagName("h1"));
        assertThat(header.getText()).isEqualTo("API Testing Interface");

        assertThat(driver.findElements(By.className("card")).size())
                .isGreaterThanOrEqualTo(5);
    }

    @Test
    void getAllUsersButtonWorks() throws InterruptedException {
        driver.get(getBaseUrl());

        WebElement button = driver.findElement(By.id("getAllUsers"));
        button.click();

        Thread.sleep(1000);

        WebElement result = driver.findElement(By.id("result1"));
        String resultText = result.getText();

        assertThat(resultText).contains("Alice");
        assertThat(resultText).contains("Bob");
    }
}