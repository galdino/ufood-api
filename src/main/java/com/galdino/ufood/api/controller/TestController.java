package com.galdino.ufood.api.controller;

import com.galdino.ufood.domain.model.Kitchen;
import com.galdino.ufood.domain.model.Restaurant;
import com.galdino.ufood.domain.repository.KitchenRepository;
import com.galdino.ufood.domain.repository.RestaurantRepository;
import com.galdino.ufood.infrastructure.repository.spec.RestaurantWithFreeDeliverySpec;
import com.galdino.ufood.infrastructure.repository.spec.RestaurantWithNameSpec;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

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

    @GetMapping("/kitchens/exists")
    public boolean kitchenExists(String name) {
        return kitchenRepository.existsByName(name);
    }

    @GetMapping("/restaurants")
    public List<Restaurant> findDeliveryFee(BigDecimal initialFee, BigDecimal finalFee) {
        return restaurantRepository.queryByDeliveryFeeBetween(initialFee, finalFee);
    }

    @GetMapping("/restaurants/by-name")
    public List<Restaurant> findByNameAndKitchen(String name, Long kitchenId) {
        return restaurantRepository.findByNameQuery(name, kitchenId);
    }

    @GetMapping("/restaurants/first-by-name")
    public Optional<Restaurant> findFirstByName(String name) {
        return restaurantRepository.findFirstRestaurantByNameContaining(name);
    }

    @GetMapping("/restaurants/top-by-name")
    public List<Restaurant> findTopByName(String name) {
        return restaurantRepository.findTop2ByNameContaining(name);
    }

    @GetMapping("/restaurants/by-name-fee")
    public List<Restaurant> findByNameAndFee(String name, BigDecimal initialFee, BigDecimal finalFee) {
        return restaurantRepository.find(name, initialFee, finalFee);
    }

    @GetMapping("/restaurants/count")
    public int countByKitchen(Long kitchenId) {
        return restaurantRepository.countByKitchenId(kitchenId);
    }

    @GetMapping("/restaurants/free-delivery")
    public List<Restaurant> findFreeDelivery(String name) {
        var withFreeDelivery = new RestaurantWithFreeDeliverySpec();
        var withName = new RestaurantWithNameSpec(name);

        return restaurantRepository.findAll(withFreeDelivery.and(withName));
    }
}
