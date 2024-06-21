package org.kirzhoy;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.baseURI;
import static org.hamcrest.Matchers.containsString;

public class AuthApiTest {

    @BeforeEach
    void beforeEach() {
        baseURI = "https://test-stand.gb.ru/gateway/login";
    }

    @Test
    @DisplayName("Проверка авторизации пользователя с валидными даными")
    public void validUserLoginTest() {
        RestAssured.given()
                .formParam("username", "Mary54")
                .formParam("password", "fb37b90703")
                .expect()
                .statusCode(200)
                .contentType("text/plain;charset=UTF-8")
                .body(containsString("5e628b1b975f9cb05b9f83eb0a2a7da5"))
                .when()
                .post(baseURI);
    }

    @Test
    @DisplayName("Проверка авторизации незарегистрированного пользователя")
    public void unregisteredUserLoginTest() {
        RestAssured.given()
                .formParam("username", "liurnvrk")
                .formParam("password", "fb37b90703")
                .expect()
                .statusCode(401)
                .body(containsString("Invalid credentials."))
                .contentType("application/json; charset=utf-8")
                .when()
                .post(baseURI);
    }

    @Test
    @DisplayName("Проверка авторизации с пустым полем логина")
    public void EmptyFieldLoginTest() {
        RestAssured.given()
                .formParam("username", "null")
                .formParam("password", "fb37b90703")
                .expect()
                .statusCode(401)
                .body(containsString("Invalid credentials."))
                .contentType("application/json; charset=utf-8")
                .when()
                .post(baseURI);
    }

}
