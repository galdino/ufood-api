package com.galdino.ufood.core.validation.uOrderInput;

import com.galdino.ufood.api.v1.model.UOrderInput;
import com.galdino.ufood.domain.exception.RestaurantNotFoundException;
import com.galdino.ufood.domain.model.Restaurant;

import java.util.Optional;

public class UOIRestaurantValidator implements UOrderInputValidator {

    private final Optional<Restaurant> restaurant;
    public UOIRestaurantValidator(Optional<Restaurant> restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public void validate(UOrderInput uOrderInput) {
        if (restaurant.isEmpty()) {
            throw new RestaurantNotFoundException(uOrderInput.getRestaurant().getId());
        }
    }
}
