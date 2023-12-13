package com.galdino.ufood.api.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.galdino.ufood.domain.exception.BusinessException;
import com.galdino.ufood.domain.exception.KitchenNotFoundException;
import com.galdino.ufood.domain.model.Restaurant;
import com.galdino.ufood.domain.repository.RestaurantRepository;
import com.galdino.ufood.domain.service.RestaurantRegisterService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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
    public Restaurant add(@RequestBody @Valid Restaurant restaurant) {
        try {
            return restaurantRegisterService.add(restaurant);
        } catch (KitchenNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @PutMapping("/{id}")
    public Restaurant update(@PathVariable Long id, @RequestBody @Valid Restaurant restaurant) {
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
    public Restaurant partialUpdate(@PathVariable Long id, @RequestBody Map<String, Object> fields, HttpServletRequest request) {
        Restaurant restaurantAux = restaurantRegisterService.findOrThrow(id);

        merge(fields, restaurantAux, request);

        return update(id, restaurantAux);
    }

    private void merge(Map<String, Object> fields, Restaurant restaurantAux, HttpServletRequest request) {
        ServletServerHttpRequest servletServerHttpRequest = new ServletServerHttpRequest(request);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

            Restaurant requestResta = objectMapper.convertValue(fields, Restaurant.class);

            fields.forEach((propertyName, propertyValue) -> {
                Field field = ReflectionUtils.findField(Restaurant.class, propertyName);
                field.setAccessible(true);

                Object newValue = ReflectionUtils.getField(field, requestResta);

                ReflectionUtils.setField(field, restaurantAux, newValue);
            });
        } catch (IllegalArgumentException e) {
            Throwable rootCause = ExceptionUtils.getRootCause(e);
            throw new HttpMessageNotReadableException(e.getMessage(), rootCause, servletServerHttpRequest);
        }
    }
}
