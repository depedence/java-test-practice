package com.example;

import com.example.pages.HomePage;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HomePageTest extends UiTestBase {

    @Test
    void homePageLoadSuccessfully() {
        HomePage homePage = new HomePage(driver);
        homePage.open(getBaseUrl());

        assertThat(homePage.getTitle()).contains("Test API Interface");
        assertThat(homePage.getHeaderText()).isEqualTo("API Testing Interface");
    }

    @Test
    void getAllUsersButtonWorks() {
        HomePage homePage = new HomePage(driver);
        homePage.open(getBaseUrl());

        homePage.clickGetAllUsers();
        String result = homePage.getAllUsersResult();

        assertThat(result).contains("Alice");
        assertThat(result).contains("Bob");
    }
}