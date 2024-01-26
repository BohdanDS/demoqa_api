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
import static io.qameta.allure.Allure.step;

public class ProfileBookListTests extends TestBase {

    String isbnId = "9781449331818";

    @Test
    @DisplayName("Check that specified book was added to user list")
    void addBookToProfileTest() {
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
    void deleteSpecifiedBookById() {
        LoginResponseModel authResponse = step("Login API request", AuthorizationApi::Login);
        step("Remove all books from user list", () -> BookApi.deleteAllBooks(authResponse.getUserId(), authResponse.getToken()));

        List<IsbnModel> isbns = new ArrayList<>();
        isbns.add(new IsbnModel(isbnId));
        AddBookRequestModel addBookRequest = new AddBookRequestModel(authResponse.getUserId(), isbns);

        DeleteSpecifiedBookRequestModel deleteBook = new DeleteSpecifiedBookRequestModel();
        deleteBook.setUserId(authResponse.getUserId());
        deleteBook.setIsbn(isbnId);

        step("Add specified book to user List", () -> BookApi.addBookToCard(addBookRequest, authResponse.getToken()));

        open("/favicon.ico");
        getWebDriver().manage().addCookie(new Cookie("expires", authResponse.getExpires()));
        getWebDriver().manage().addCookie(new Cookie("userID", authResponse.getUserId()));
        getWebDriver().manage().addCookie(new Cookie("token", authResponse.getToken()));

        step("Checks that added book is displayed in user profile", () ->
                {
                    open("/profile");
                    $(".ReactTable").shouldHave(text("Learning JavaScript Design Patterns"));
                }
        );

        step("Remove specified book by Id from user profile", () -> BookApi.deleteBookFromCartById(deleteBook, authResponse.getToken()));

        step("Check that removed books is not displayed in user profile",()->{
            Selenide.refresh();
            $(".rt-noData").shouldHave(text("No rows found"));
        });

    }

}
