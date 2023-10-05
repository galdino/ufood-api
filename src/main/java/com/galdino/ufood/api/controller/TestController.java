package com.galdino.ufood.api.controller;

import com.galdino.ufood.domain.model.Kitchen;
import com.galdino.ufood.domain.model.Restaurant;
import com.galdino.ufood.domain.repository.KitchenRepository;
import com.galdino.ufood.domain.repository.RestaurantRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

    private KitchenRepository kitchenRepository;
    private RestaurantRepository restaurantRepository;

    public TestController(KitchenRepository kitchenRepository, RestaurantRepository restaurantRepository) {
        this.kitchenRepository = kitchenRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @GetMapping("/kitchens")
    public List<Kitchen> findByName(@RequestParam("name") String name) {
        return kitchenRepository.findByNameContaining(name);
    }

    @GetMapping("/restaurants")
    public List<Restaurant> findDeliveryFee(BigDecimal initialFee, BigDecimal finalFee) {
        return restaurantRepository.findByDeliveryFeeBetween(initialFee, finalFee);
    }

    @GetMapping("/restaurants/by-name")
    public List<Restaurant> findByNameAndKitchen(String name, Long kitchenId) {
        return restaurantRepository.findByNameContainingAndKitchenId(name, kitchenId);
    }
}
