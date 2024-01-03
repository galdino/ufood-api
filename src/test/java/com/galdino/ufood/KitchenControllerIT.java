package com.galdino.ufood;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class KitchenControllerIT {

    @LocalServerPort
    private int port;

    @Test
    public void shouldReturnStatus200_WhenGetKitchens() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

            given()
                .basePath("/kitchens")
                .port(port)
                .accept(ContentType.JSON)
            .when()
                .get()
            .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void shouldHave2Kitchens_WhenGetKitchens() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

            given()
                .basePath("/kitchens")
                .port(port)
                .accept(ContentType.JSON)
            .when()
                .get()
            .then()
                .body("", Matchers.hasSize(2))
                .body("name", Matchers.hasItems("Thai", "Indian"));

    }

}
