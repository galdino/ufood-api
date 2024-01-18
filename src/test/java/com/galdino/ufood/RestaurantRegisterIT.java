package com.galdino.ufood;

import com.galdino.ufood.domain.exception.EntityInUseException;
import com.galdino.ufood.domain.exception.KitchenNotFoundException;
import com.galdino.ufood.domain.exception.RestaurantNotFoundException;
import com.galdino.ufood.domain.model.Kitchen;
import com.galdino.ufood.domain.model.Restaurant;
import com.galdino.ufood.domain.service.RestaurantRegisterService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RestaurantRegisterIT {

    @Autowired
    private RestaurantRegisterService restaurantRegisterService;

    @Test
    public void restaurantRegisterWithSuccess() {
        Restaurant newRestaurant = new Restaurant();

        Kitchen kitchen = new Kitchen();
        kitchen.setId(1L);

        newRestaurant.setName("Thai Hot");
        newRestaurant.setDeliveryFee(new BigDecimal("5"));
        newRestaurant.setKitchen(kitchen);


        newRestaurant = restaurantRegisterService.add(newRestaurant);

        assertThat(newRestaurant).isNotNull();
        assertThat(newRestaurant.getId()).isNotNull();

    }

    @Test(expected = ConstraintViolationException.class)
    public void shouldFail_WhenRestaurantRegisterWithoutName() {
        Restaurant newRestaurant = new Restaurant();

        Kitchen kitchen = new Kitchen();
        kitchen.setId(1L);

        newRestaurant.setName("");
        newRestaurant.setDeliveryFee(new BigDecimal("5"));
        newRestaurant.setKitchen(kitchen);


        newRestaurant = restaurantRegisterService.add(newRestaurant);
    }

    @Test(expected = ConstraintViolationException.class)
    public void shouldFail_WhenRestaurantRegisterWithInvalidDeliveryFee() {
        Restaurant newRestaurant = new Restaurant();

        Kitchen kitchen = new Kitchen();
        kitchen.setId(1L);

        newRestaurant.setName("Thai Hot");
        newRestaurant.setDeliveryFee(new BigDecimal("2"));
        newRestaurant.setKitchen(kitchen);


        newRestaurant = restaurantRegisterService.add(newRestaurant);
    }

    @Test(expected = KitchenNotFoundException.class)
    public void shouldFail_WhenRestaurantRegisterWithInvalidKitchen() {
        Restaurant newRestaurant = new Restaurant();

        Kitchen kitchen = new Kitchen();
        kitchen.setId(KitchenControllerIT.KITCHEN_ID_NOT_FOUND);

        newRestaurant.setName("Thai Hot");
        newRestaurant.setDeliveryFee(new BigDecimal("5"));
        newRestaurant.setKitchen(kitchen);


        newRestaurant = restaurantRegisterService.add(newRestaurant);
    }

    @Test(expected = EntityInUseException.class)
    public void shouldFail_WhenRemoveRestaurantInUse() {
        Long id = 1L;

        restaurantRegisterService.remove(id);

    }

    @Test(expected = RestaurantNotFoundException.class)
    public void shouldFail_WhenRemoveRestaurantNotFound() {
        Long id = 9999L;

        restaurantRegisterService.remove(id);

    }
}
