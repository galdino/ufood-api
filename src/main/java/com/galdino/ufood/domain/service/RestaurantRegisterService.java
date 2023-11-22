package com.galdino.ufood.domain.service;

import com.galdino.ufood.domain.exception.EntityInUseException;
import com.galdino.ufood.domain.exception.UEntityNotFoundException;
import com.galdino.ufood.domain.model.Kitchen;
import com.galdino.ufood.domain.model.Restaurant;
import com.galdino.ufood.domain.repository.RestaurantRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class RestaurantRegisterService {

    public static final String UNABLE_TO_FIND_RESTAURANT = "Unable to find restaurant with id %d";
    public static final String RESTAURANT_IN_USE = "Restaurant with id %d cannot be removed, it is in use.";

    private RestaurantRepository restaurantRepository;
    private KitchenRegisterService kitchenRegisterService;

    public RestaurantRegisterService(RestaurantRepository restaurantRepository, KitchenRegisterService kitchenRegisterService) {
        this.restaurantRepository = restaurantRepository;
        this.kitchenRegisterService = kitchenRegisterService;
    }

    public Restaurant add(Restaurant restaurant) {
        Long kitchenId = restaurant.getKitchen().getId();
        Kitchen kitchen = kitchenRegisterService.findOrThrow(kitchenId);

        restaurant.setKitchen(kitchen);

        return restaurantRepository.save(restaurant);
    }

    public void remove(Long id) {
        try {
            restaurantRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new UEntityNotFoundException(String.format(UNABLE_TO_FIND_RESTAURANT, id));
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(String.format(RESTAURANT_IN_USE, id));
        }
    }

    public Restaurant findOrThrow(Long id) {
        return restaurantRepository.findById(id).orElseThrow(() -> new UEntityNotFoundException(String.format(UNABLE_TO_FIND_RESTAURANT, id)));
    }
}
