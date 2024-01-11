package com.galdino.ufood;

import com.galdino.ufood.domain.model.Kitchen;
import com.galdino.ufood.domain.repository.KitchenRepository;
import com.galdino.ufood.util.DatabaseCleaner;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class KitchenControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    private KitchenRepository kitchenRepository;

    @Before
    public void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/kitchens";

        databaseCleaner.clearTables();
        prepareData();
    }

    @Test
    public void shouldReturnStatus200_WhenGetKitchens() {
            given()
                .accept(ContentType.JSON)
            .when()
                .get()
            .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void shouldHave2Kitchens_WhenGetKitchens() {
            given()
                .accept(ContentType.JSON)
            .when()
                .get()
            .then()
                .body("", Matchers.hasSize(2))
                .body("name", Matchers.hasItems("Thai", "Indian"));
    }

    @Test
    public void shoulReturnStatus201_WhenRegisterKitchen() {
            given()
                .body("{ \"name\": \"Chinese\" }")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
            .when()
                .post()
            .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void shouldReturnKitchenAndStatus200_WhenGetWithKitchenId() {
        given()
            .pathParam("kitchenId", 1)
            .accept(ContentType.JSON)
        .when()
            .get("/{kitchenId}")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("name", Matchers.equalTo("Thai"));
    }

    @Test
    public void shouldReturnStatus404_WhenGetKitchenNotFound() {
        given()
                .pathParam("kitchenId", 100)
                .accept(ContentType.JSON)
                .when()
                .get("/{kitchenId}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    private void prepareData() {
        Kitchen kitchen1 = new Kitchen();
        kitchen1.setName("Thai");

        Kitchen kitchen2 = new Kitchen();
        kitchen2.setName("Indian");

        kitchenRepository.save(kitchen1);
        kitchenRepository.save(kitchen2);
    }

}
