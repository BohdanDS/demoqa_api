package com.demoqa.API;

import com.demoqa.models.LoginBodyModel;
import com.demoqa.models.LoginResponseModel;


import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static com.demoqa.specs.Specifications.requestSpecification;
import static com.demoqa.specs.Specifications.responseSpecification;
import static io.restassured.RestAssured.given;


public class AuthorizationApi {
    public static LoginResponseModel Login(){

        LoginBodyModel loginData = new LoginBodyModel();
        loginData.setUserName("bohdanP");
        loginData.setPassword("bohdanP!1");

        return given(requestSpecification)
                    .body(loginData)
                .when()
                    .post("/Account/v1/Login")
                .then()
                .spec(responseSpecification)
                .statusCode(200)
                .extract().as(LoginResponseModel.class);
    }

    public static void Logout(){
        open("/favicon.ico");
        getWebDriver().manage().deleteAllCookies();
    }
}
