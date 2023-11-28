package com.galdino.ufood.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.galdino.ufood.domain.exception.BusinessException;
import com.galdino.ufood.domain.exception.KitchenNotFoundException;
import com.galdino.ufood.domain.model.Restaurant;
import com.galdino.ufood.domain.repository.RestaurantRepository;
import com.galdino.ufood.domain.service.RestaurantRegisterService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/restaurants")
public class RestaurantController {

    private RestaurantRepository restaurantRepository;
    private RestaurantRegisterService restaurantRegisterService;

    public RestaurantController(RestaurantRepository restaurantRepository, RestaurantRegisterService restaurantRegisterService) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantRegisterService = restaurantRegisterService;
    }

    @GetMapping
    public List<Restaurant> list() {
        return restaurantRepository.findAll();
    }

    @GetMapping("/{id}")
    public Restaurant findById(@PathVariable Long id) {
        return restaurantRegisterService.findOrThrow(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Restaurant add(@RequestBody Restaurant restaurant) {
        try {
            return restaurantRegisterService.add(restaurant);
        } catch (KitchenNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @PutMapping("/{id}")
    public Restaurant update(@PathVariable Long id, @RequestBody Restaurant restaurant) {
        Restaurant restaurantAux = restaurantRegisterService.findOrThrow(id);

        BeanUtils.copyProperties(restaurant, restaurantAux, "id", "paymentMethods", "address", "registerDate", "products");

        try {
            return restaurantRegisterService.add(restaurantAux);
        } catch (KitchenNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long id) {
            restaurantRegisterService.remove(id);
    }

    @PatchMapping("/{id}")
    public Restaurant partialUpdate(@PathVariable Long id, @RequestBody Map<String, Object> fields) {
        Restaurant restaurantAux = restaurantRegisterService.findOrThrow(id);

        merge(fields, restaurantAux);

        return update(id, restaurantAux);
    }

    private void merge(Map<String, Object> fields, Restaurant restaurantAux) {
        ObjectMapper objectMapper = new ObjectMapper();
        Restaurant requestResta = objectMapper.convertValue(fields, Restaurant.class);

        fields.forEach((propertyName, propertyValue) -> {
            Field field = ReflectionUtils.findField(Restaurant.class, propertyName);
            field.setAccessible(true);

            Object newValue = ReflectionUtils.getField(field, requestResta);

            ReflectionUtils.setField(field, restaurantAux, newValue);
        });
    }
}
