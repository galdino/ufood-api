package com.galdino.ufood;

import com.galdino.ufood.domain.model.Kitchen;
import com.galdino.ufood.domain.repository.KitchenRepository;
import com.galdino.ufood.util.DatabaseCleaner;
import com.galdino.ufood.util.ResourceUtils;
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

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class KitchenControllerIT {

    public static final int KITCHEN_ID_NOT_FOUND = 100;

    @LocalServerPort
    private int port;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    private KitchenRepository kitchenRepository;

    private List<Kitchen> kitchens;
    private Kitchen kitchen1;

    @Before
    public void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/kitchens";

        databaseCleaner.clearTables();

        kitchens = new ArrayList<>();

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
    public void shouldHaveSizeKitchens_WhenGetKitchens() {
            given()
                .accept(ContentType.JSON)
            .when()
                .get()
            .then()
                .body("", Matchers.hasSize(kitchens.size()));
    }

    @Test
    public void shoulReturnStatus201_WhenRegisterKitchen() {
            given()
                .body(ResourceUtils.getContentFromResource("/json/newKitchen.json"))
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
            .pathParam("kitchenId", kitchen1.getId())
            .accept(ContentType.JSON)
        .when()
            .get("/{kitchenId}")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("name", Matchers.equalTo(kitchen1.getName()));
    }

    @Test
    public void shouldReturnStatus404_WhenGetKitchenNotFound() {
        given()
            .pathParam("kitchenId", KITCHEN_ID_NOT_FOUND)
            .accept(ContentType.JSON)
        .when()
            .get("/{kitchenId}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    private void prepareData() {
        kitchen1 = new Kitchen();
        kitchen1.setName("Thai");

        Kitchen kitchen2 = new Kitchen();
        kitchen2.setName("Indian");

        kitchenRepository.save(kitchen1);
        kitchenRepository.save(kitchen2);

        kitchens = kitchenRepository.findAll();
    }

}
