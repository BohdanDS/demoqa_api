package com.demoqa.tests;

import com.demoqa.API.AuthorizationApi;
import com.demoqa.models.LoginResponseModel;
import org.openqa.selenium.Cookie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class LoginTests extends TestBase {
    @Test
    @DisplayName("Authorization using login API")
    void successfulLoginWithApiTest(){
        LoginResponseModel authResponse = AuthorizationApi.Login();

         open("/favicon.ico");
         getWebDriver().manage().addCookie(new Cookie("expires", authResponse.getExpires()));
         getWebDriver().manage().addCookie(new Cookie("userID", authResponse.getUserId()));
         getWebDriver().manage().addCookie(new Cookie("token", authResponse.getToken()));


        open("/profile");
        $("#userName-value").shouldHave(text("bohdanP"));
    }
}
