package com.galdino.ufood.core.validation.uOrderInput;

import com.galdino.ufood.api.model.UOrderInput;
import com.galdino.ufood.domain.exception.BusinessException;
import com.galdino.ufood.domain.model.Product;
import com.galdino.ufood.domain.model.Restaurant;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UOIRestaurantProductValidator implements UOrderInputValidator {

    private final Optional<Restaurant> restaurant;

    public UOIRestaurantProductValidator(Optional<Restaurant> restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public void validate(UOrderInput uOrderInput) {
        if (restaurant.isPresent()) {

            Restaurant restaurantAux = restaurant.get();

            List<Long> productsId = restaurantAux.getProducts().stream()
                                                               .map(Product::getId)
                                                               .collect(Collectors.toList());

            uOrderInput.getItems().forEach(item -> {
                if (!productsId.contains(item.getProductId())) {
                    throw new BusinessException(String.format("The restaurant with id %d does not offer the product with id %d", restaurantAux.getId(), item.getProductId()));
                }
            });

        }
    }
}
