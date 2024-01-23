package com.demoqa.API;

import com.demoqa.models.AddBookRequestModel;
import com.demoqa.models.DeleteSpecifiedBookRequestModel;

import static com.demoqa.specs.Specifications.requestSpecification;
import static com.demoqa.specs.Specifications.responseSpecification;
import static io.restassured.RestAssured.given;


public class BookApi {

    public static void deleteAllBooks(String userId, String authToken){

        given(requestSpecification)
                .queryParam("UserId", userId)
                .header("Authorization", "Bearer "+authToken)
        .when().
                delete("/BookStore/v1/Books")
        .then()
                .spec(responseSpecification)
                .statusCode(204);

    }

    public static void addBookToCard(AddBookRequestModel addBookRequest, String authToken){

        given(requestSpecification)
                .header("Authorization", "Bearer "+authToken)
                .body(addBookRequest)
        .when()
                .post("/BookStore/v1/Books")
        .then()
                .spec(responseSpecification)
                .statusCode(201);
    }

    public static void deleteBookFromCartById(DeleteSpecifiedBookRequestModel deleteBookRequest, String authToken){
        given(requestSpecification)
                .header("Authorization", "Bearer "+authToken)
                .body(deleteBookRequest)
        .when()
                .delete("/BookStore/v1/Book")
        .then()
                .spec(responseSpecification)
                .statusCode(204);
    }

}
