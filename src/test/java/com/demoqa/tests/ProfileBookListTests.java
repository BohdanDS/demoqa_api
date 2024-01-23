package com.demoqa.tests;

import com.codeborne.selenide.Selenide;
import com.demoqa.API.AuthorizationApi;
import com.demoqa.API.BookApi;
import com.demoqa.models.AddBookRequestModel;
import com.demoqa.models.DeleteSpecifiedBookRequestModel;
import com.demoqa.models.IsbnModel;
import com.demoqa.models.LoginResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class ProfileBookListTests extends TestBase {

    String isbnId = "9781449331818";

    @Test
    @DisplayName("Check that specified book was added to user list")
    void addBookToProfileTest(){
        LoginResponseModel authResponse = AuthorizationApi.Login();

        List<IsbnModel> isbns = new ArrayList<>();
        isbns.add(new IsbnModel(isbnId));
        AddBookRequestModel addBookRequest = new AddBookRequestModel(authResponse.getUserId(), isbns);

        BookApi.deleteAllBooks(authResponse.getUserId(), authResponse.getToken());
        BookApi.addBookToCard(addBookRequest, authResponse.getToken());

        open("/favicon.ico");
        getWebDriver().manage().addCookie(new Cookie("expires", authResponse.getExpires()));
        getWebDriver().manage().addCookie(new Cookie("userID", authResponse.getUserId()));
        getWebDriver().manage().addCookie(new Cookie("token", authResponse.getToken()));

        open("/profile");
        $(".ReactTable").shouldHave(text("Learning JavaScript Design Patterns"));
    }

    @Test
    @DisplayName("Delete specified book from user list")
    void deleteSpecifiedBookById(){
        LoginResponseModel authResponse = AuthorizationApi.Login();
        BookApi.deleteAllBooks(authResponse.getUserId(), authResponse.getToken());

        List<IsbnModel> isbns = new ArrayList<>();
        isbns.add(new IsbnModel(isbnId));
        AddBookRequestModel addBookRequest = new AddBookRequestModel(authResponse.getUserId(), isbns);

        DeleteSpecifiedBookRequestModel deleteBook = new DeleteSpecifiedBookRequestModel();
        deleteBook.setUserId(authResponse.getUserId());
        deleteBook.setIsbn(isbnId);

        BookApi.addBookToCard(addBookRequest, authResponse.getToken());

        open("/favicon.ico");
        getWebDriver().manage().addCookie(new Cookie("expires", authResponse.getExpires()));
        getWebDriver().manage().addCookie(new Cookie("userID", authResponse.getUserId()));
        getWebDriver().manage().addCookie(new Cookie("token", authResponse.getToken()));

        open("/profile");
        $(".ReactTable").shouldHave(text("Learning JavaScript Design Patterns"));

        BookApi.deleteBookFromCartById(deleteBook, authResponse.getToken());

        Selenide.refresh();
        $(".rt-noData").shouldHave(text("No rows found"));

    }

}
