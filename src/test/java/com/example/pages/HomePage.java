package com.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Locators
    @FindBy(tagName = "h1")
    private WebElement pageHeader;

    @FindBy(id = "getAllUsers")
    private WebElement getAllUsersButton;

    @FindBy(id = "result1")
    private WebElement getAllUsersResult;

    @FindBy(id = "getUser")
    private WebElement getUserButton;

    @FindBy(id = "userId")
    private WebElement userIdInput;

    @FindBy(id = "result2")
    private WebElement getUserResult;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void open(String baseUrl) {
        driver.get(baseUrl);
        wait.until(d -> pageHeader.isDisplayed());
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public String getHeaderText() {
        return pageHeader.getText();
    }

    public void clickGetAllUsers() {
        getAllUsersButton.click();
        wait.until(d -> !getAllUsersResult.getText().isEmpty());
    }

    public String getAllUsersResult() {
        return getAllUsersResult.getText();
    }

    public void getUserById(int id) {
        userIdInput.clear();
        userIdInput.sendKeys(String.valueOf(id));
        getUserButton.click();
        wait.until(d -> !getUserResult.getText().isEmpty());
    }

    public String getUserResult() {
        return getUserResult.getText();
    }

    public boolean isButtonVisible(String buttonText) {
        try {
            return driver.findElement(
                    By.xpath("//button[contains(text(), '" + buttonText + "')]")
            ).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}