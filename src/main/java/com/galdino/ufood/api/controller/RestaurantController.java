package com.galdino.ufood.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.galdino.ufood.domain.model.Restaurant;
import com.galdino.ufood.domain.repository.RestaurantRepository;
import com.galdino.ufood.domain.service.RestaurantRegisterService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
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
        return restaurantRepository.list();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> findById(@PathVariable Long id) {
        Restaurant restaurant = restaurantRepository.findById(id);

        if (restaurant == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(restaurant);
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody Restaurant restaurant) {
        try {
            Restaurant restaurantAux = restaurantRegisterService.add(restaurant);

            return ResponseEntity.status(HttpStatus.CREATED).body(restaurantAux);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Restaurant restaurant) {
        Restaurant restaurantAux = restaurantRepository.findById(id);

        if (restaurantAux == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        BeanUtils.copyProperties(restaurant, restaurantAux, "id");

        try {
            restaurantAux = restaurantRegisterService.add(restaurantAux);

            return ResponseEntity.status(HttpStatus.OK).body(restaurantAux);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Restaurant> remove(@PathVariable Long id) {
        try {
            restaurantRegisterService.remove(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> partialUpdate(@PathVariable Long id, @RequestBody Map<String, Object> fields) {
        Restaurant restaurantAux = restaurantRepository.findById(id);

        if (restaurantAux == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

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