package com.demoqa.specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static com.demoqa.helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;
import static io.restassured.http.ContentType.JSON;

public class Specifications {
    public static RequestSpecification requestSpecification = with()
            .filter(withCustomTemplates())
            .contentType(JSON)
            .log().uri()
            .log().body();

    public static ResponseSpecification responseSpecification = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .build();
}
