package com.galdino.ufood.domain.service;

import com.galdino.ufood.domain.exception.EntityInUseException;
import com.galdino.ufood.domain.exception.RestaurantNotFoundException;
import com.galdino.ufood.domain.model.Kitchen;
import com.galdino.ufood.domain.model.Restaurant;
import com.galdino.ufood.domain.repository.RestaurantRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RestaurantRegisterService {

    public static final String RESTAURANT_IN_USE = "Restaurant with id %d cannot be removed, it is in use.";

    private RestaurantRepository restaurantRepository;
    private KitchenRegisterService kitchenRegisterService;

    public RestaurantRegisterService(RestaurantRepository restaurantRepository, KitchenRegisterService kitchenRegisterService) {
        this.restaurantRepository = restaurantRepository;
        this.kitchenRegisterService = kitchenRegisterService;
    }

    @Transactional
    public Restaurant add(Restaurant restaurant) {
        Long kitchenId = restaurant.getKitchen().getId();
        Kitchen kitchen = kitchenRegisterService.findOrThrow(kitchenId);

        restaurant.setKitchen(kitchen);

        return restaurantRepository.save(restaurant);
    }

    @Transactional
    public void remove(Long id) {
        try {
            restaurantRepository.deleteById(id);
            restaurantRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new RestaurantNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(String.format(RESTAURANT_IN_USE, id));
        }
    }

    public Restaurant findOrThrow(Long id) {
        return restaurantRepository.findById(id).orElseThrow(() -> new RestaurantNotFoundException(id));
    }
}
