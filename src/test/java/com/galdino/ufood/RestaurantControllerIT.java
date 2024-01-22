package com.galdino.ufood;

import com.galdino.ufood.api.exceptionhandler.ProblemType;
import com.galdino.ufood.domain.model.Kitchen;
import com.galdino.ufood.domain.model.Restaurant;
import com.galdino.ufood.domain.repository.KitchenRepository;
import com.galdino.ufood.domain.repository.RestaurantRepository;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class RestaurantControllerIT {

    private static final long RESTAURANT_ID_NOT_FOUND = 9999;

    @LocalServerPort
    private int port;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private KitchenRepository kitchenRepository;
    
    private List<Restaurant> restaurants;
    private Restaurant restaurant1;

    @Before
    public void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/restaurants";

        databaseCleaner.clearTables();

        restaurants = new ArrayList<>();

        prepareData();
    }

    @Test
    public void shouldReturnStatus200_WhenGetRestaurants() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void shouldHaveSizeRestaurants_WhenGetRestaurants() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .body("", Matchers.hasSize(restaurants.size()));
    }

    @Test
    public void shouldReturnStatus201_WhenRegisterRestaurant() {
        given()
            .body(ResourceUtils.getContentFromResource("/json/newRestaurant.json"))
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void shouldReturnRestaurantAndStatus200_WhenGetWithRestaurantId() {
        given()
            .pathParam("id", restaurant1.getId())
            .contentType(ContentType.JSON)
        .when()
            .get("/{id}")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("name", Matchers.equalTo(restaurant1.getName()));
    }

    @Test
    public void shouldReturnStatus404_WhenGetRestaurantNotFound() {
        given()
            .pathParam("id", RESTAURANT_ID_NOT_FOUND)
            .contentType(ContentType.JSON)
        .when()
            .get("/{id}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void shouldReturnStatus400_WhenRegisterRestaurantWithoutDeliveryFee() {
        given()
            .body(ResourceUtils.getContentFromResource("/json/newRestaurantWithoutDeliveryFee.json"))
            .accept(ContentType.JSON)
            .contentType(ContentType.JSON)
        .when()
            .post()
        .then()
            .body("title", Matchers.equalTo(ProblemType.INVALID_FIELD.getTitle()))
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void shouldReturn400_WhenRegisterRestaurantWithoutKitchen() {
        given()
            .body(ResourceUtils.getContentFromResource("/json/newRestaurantWithoutKitchen.json"))
            .accept(ContentType.JSON)
            .contentType(ContentType.JSON)
        .when()
            .post()
        .then()
            .body("title", Matchers.equalTo(ProblemType.INVALID_FIELD.getTitle()))
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void shouldReturn400_WhenRegisterRestaurantWithKitchenNotFound() {
        given()
            .body(ResourceUtils.getContentFromResource("/json/newRestaurantWithKitchenNotFound.json"))
            .accept(ContentType.JSON)
            .contentType(ContentType.JSON)
        .when()
            .post()
        .then()
            .body("title", Matchers.equalTo(ProblemType.BUSINESS_ERROR.getTitle()))
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    private void prepareData() {
        Kitchen kitchen = new Kitchen();
        kitchen.setName("Thai");

        kitchenRepository.save(kitchen);

        restaurant1 = new Restaurant();
        restaurant1.setName("Thai Taste");
        restaurant1.setDeliveryFee(new BigDecimal("5"));
        restaurant1.setKitchen(new Kitchen());
        restaurant1.getKitchen().setId(1L);

        Restaurant restaurant2 = new Restaurant();
        restaurant2.setName("Thai Hot");
        restaurant2.setDeliveryFee(new BigDecimal("5"));
        restaurant2.setKitchen(new Kitchen());
        restaurant2.getKitchen().setId(1L);

        restaurantRepository.save(restaurant1);
        restaurantRepository.save(restaurant2);

        restaurants = restaurantRepository.findAll();
    }

}
