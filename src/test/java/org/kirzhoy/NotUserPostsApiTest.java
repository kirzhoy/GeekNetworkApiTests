package org.kirzhoy;

import io.restassured.RestAssured;
import org.hamcrest.collection.IsEmptyCollection;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.baseURI;
import static org.hamcrest.Matchers.*;

public class NotUserPostsApiTest extends AbstractTest {

    @Test
    @DisplayName("Проверка вызова ленты чужих постов с первой страницы")
    public void notOwnerPostsTest() {
        RestAssured.given()
                .spec(requestSpecificationNotOwnerPosts)
                .queryParam("page", "1")
                .expect()
                .statusCode(200)
                .contentType("application/json")
                .body("size()", greaterThan(0))
                .when()
                .get(baseURI);
    }

    @Test
    @DisplayName("Проверка вызова пустой страницы в общей ленте постов")
    public void notOwnerPostsEmptyPageTest() {
        RestAssured.given()
                .spec(requestSpecificationNotOwnerPosts)
                .queryParam("page", "25000")
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
    @DisplayName("Проверка количества постов на одной странице")
    public void notOwnerPostsAreFourOnPage() {
        RestAssured.given()
                .spec(requestSpecificationNotOwnerPosts)
                .queryParam("page", "1")
                .expect()
                .statusCode(200)
                .contentType("application/json")
                .body("data.size()", equalTo(4))
                .when()
                .get(baseURI);
    }

    @Test
    @DisplayName("Проверка вызова произвольной страницы")
    public void notOwnerPostsArbitraryPageTest() {
        RestAssured.given()
                .spec(requestSpecificationNotOwnerPosts)
                .queryParam("page", "15")
                .expect()
                .statusCode(200)
                .contentType("application/json")
                .body("size()", greaterThan(0))
                .body("meta.nextPage", equalTo(16))
                .body("meta.prevPage", equalTo(14))
                .when()
                .get(baseURI);
    }

    @Test
    @DisplayName("Проверка вызова ленты постов без query параметров")
    public void notOwnerPostsTestWithoutQuery() {
        RestAssured.given()
                .spec(requestSpecificationNotOwnerPosts)
                .expect()
                .statusCode(200)
                .contentType("application/json")
                .body("size()", greaterThan(0))
                .when()
                .get(baseURI);
    }
}
