package org.kirzhoy;

import io.restassured.RestAssured;
import org.hamcrest.collection.IsEmptyCollection;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.baseURI;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

public class UserPostsApiTest extends AbstractTest {

    @Test
    @DisplayName("Проверка вызова ленты своих постов с первой страницы")
    public void ownerValidPostsTest() {
        RestAssured.given()
                .spec(requestSpecificationOwnerPosts)
                .queryParam("page", "1")
                .expect()
                .statusCode(200)
                .contentType("application/json")
                .body("size()", greaterThan(0))
                .when()
                .get(baseURI);
    }

    @Test
    @DisplayName("Проверка вызова пустой страницы")
    public void ownerPostsEmptyPageTest() {
        RestAssured.given()
                .spec(requestSpecificationOwnerPosts)
                .queryParam("page", "15")
                .expect()
                .statusCode(200)
                .contentType("application/json")
                .body("size()", greaterThan(0))
                .body("data", IsEmptyCollection.empty())
                .body("nextPage", IsNull.nullValue())
                .when()
                .get(baseURI);
    }

    @Test
    @DisplayName("Проверка вызова последней страницы")
    public void ownerPostsLastPageTest() {
        RestAssured.given()
                .spec(requestSpecificationOwnerPosts)
                .queryParam("page", "3")
                .expect()
                .statusCode(200)
                .contentType("application/json")
                .body("size()", greaterThan(0))
                .body("meta.nextPage", equalTo(null))
                .when()
                .get(baseURI);
    }

    @Test
    @DisplayName("Проверка вызова ленты без query параметров")
    public void ownerPostsWithoutQueryTest() {
        RestAssured.given()
                .spec(requestSpecificationOwnerPosts)
                .expect()
                .statusCode(200)
                .contentType("application/json")
                .body("size()", greaterThan(0))
                .when()
                .get(baseURI);
    }

    @Test
    @DisplayName("Проверка вызова ленты, начиная с самого старого поста")
    public void ownerPostsSortingFromLatestTest() {
        RestAssured.given()
                .spec(requestSpecificationOwnerPosts)
                .queryParam("sort", "createdAt")
                .queryParam("order", "ASC")
                .expect()
                .contentType("application/json")
                .body("size()", greaterThan(0))
                .body("data.title[0]", equalTo("test1"))
                .body("data.title[1]", equalTo("Test2"))
                .body("data.title[3]", equalTo("Test4"))
                .when()
                .get(baseURI);
    }

}

