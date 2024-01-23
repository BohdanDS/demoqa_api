package com.demoqa.tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import com.demoqa.config.ConfigReader;
import com.demoqa.config.ProjectConfiguration;
import com.demoqa.config.web.WebConfig;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;


import static com.codeborne.selenide.Selenide.closeWebDriver;

public class TestBase {

    private static final WebConfig webConfig = ConfigReader.Instance.read();

    @BeforeAll
    static void beforeAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
        ProjectConfiguration projectConfiguration = new ProjectConfiguration(webConfig);
        projectConfiguration.webConfig();
    }

    @AfterEach
    void shutDown() {
        closeWebDriver();
    }

//    @BeforeAll
//    public static void setUp() {
//        Configuration.baseUrl = "https://demoqa.com";
//        RestAssured.baseURI = "https://demoqa.com";
//        RestAssured.basePath = "/api";
//    }
}
