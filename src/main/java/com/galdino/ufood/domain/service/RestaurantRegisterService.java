package com.galdino.ufood.domain.service;

import com.galdino.ufood.domain.model.Kitchen;
import com.galdino.ufood.domain.model.Restaurant;
import com.galdino.ufood.domain.repository.KitchenRepository;
import com.galdino.ufood.domain.repository.RestaurantRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class RestaurantRegisterService {

    private RestaurantRepository restaurantRepository;
    private KitchenRepository kitchenRepository;

    public RestaurantRegisterService(RestaurantRepository restaurantRepository, KitchenRepository kitchenRepository) {
        this.restaurantRepository = restaurantRepository;
        this.kitchenRepository = kitchenRepository;
    }

    public Restaurant add(Restaurant restaurant) {
        Long kitchenId = restaurant.getKitchen().getId();
        Kitchen kitchen = kitchenRepository.findById(kitchenId);

        if (kitchen == null) {
            throw new EntityNotFoundException(String.format("Unable to find kitchen with id %d", kitchenId));
        }

        return restaurantRepository.add(restaurant);
    }
}
