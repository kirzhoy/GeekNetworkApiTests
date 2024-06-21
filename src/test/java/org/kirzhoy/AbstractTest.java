package org.kirzhoy;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;

public class AbstractTest {
    public static RequestSpecification requestSpecificationOwnerPosts;
    public static RequestSpecification requestSpecificationNotOwnerPosts;

    @BeforeAll
    public static void initTest() {
        RestAssured.baseURI = "https://test-stand.gb.ru/api/posts";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        requestSpecificationOwnerPosts = new RequestSpecBuilder()
                .addHeader("X-Auth-Token", "5e628b1b975f9cb05b9f83eb0a2a7da5")
                .build();

        requestSpecificationNotOwnerPosts = new RequestSpecBuilder()
                .addHeader("X-Auth-Token", "5e628b1b975f9cb05b9f83eb0a2a7da5")
                .addQueryParam("owner", "notMe")
                .build();
    }
}
