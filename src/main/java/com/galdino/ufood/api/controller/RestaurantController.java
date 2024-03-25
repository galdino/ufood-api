package com.galdino.ufood.api.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.galdino.ufood.api.assembler.GenericAssembler;
import com.galdino.ufood.api.assembler.RestaurantInputDiassembler;
import com.galdino.ufood.api.assembler.RestaurantModelAssembler;
import com.galdino.ufood.api.model.KitchenIdInput;
import com.galdino.ufood.api.model.RestaurantInput;
import com.galdino.ufood.api.model.RestaurantModel;
import com.galdino.ufood.core.validation.ValidationException;
import com.galdino.ufood.domain.exception.BusinessException;
import com.galdino.ufood.domain.exception.KitchenNotFoundException;
import com.galdino.ufood.domain.model.Restaurant;
import com.galdino.ufood.domain.repository.RestaurantRepository;
import com.galdino.ufood.domain.service.RestaurantRegisterService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
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
    private SmartValidator validator;
    private GenericAssembler genericAssembler;

    public RestaurantController(RestaurantRepository restaurantRepository, RestaurantRegisterService restaurantRegisterService, SmartValidator validator,
                                RestaurantModelAssembler restaurantModelAssembler, RestaurantInputDiassembler restaurantInputDiassembler,
                                GenericAssembler genericAssembler) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantRegisterService = restaurantRegisterService;
        this.validator = validator;
        this.genericAssembler = genericAssembler;
    }

    @GetMapping
    public List<RestaurantModel> list() {
        return genericAssembler.toCollection(restaurantRepository.findAll(), RestaurantModel.class);
    }

    @GetMapping("/{id}")
    public RestaurantModel findById(@PathVariable Long id) {
        Restaurant restaurant = restaurantRegisterService.findOrThrow(id);

        return genericAssembler.toClass(restaurant, RestaurantModel.class);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestaurantModel add(@RequestBody @Valid RestaurantInput restaurantInput) {
        try {

            Restaurant restaurant = genericAssembler.toClass(restaurantInput, Restaurant.class);
            restaurant = restaurantRegisterService.add(restaurant);

            return genericAssembler.toClass(restaurant, RestaurantModel.class);
        } catch (KitchenNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @PutMapping("/{id}")
    public RestaurantModel update(@PathVariable Long id, @RequestBody @Valid RestaurantInput restaurantInput) {
        Restaurant restaurantAux = restaurantRegisterService.findOrThrow(id);

//        BeanUtils.copyProperties(restaurantInputDiassembler.toDomainObject(restaurantInput), restaurantAux,
//                                                            "id", "paymentMethods", "address",
//                                                                            "registerDate", "products");

        genericAssembler.copyToObject(restaurantInput, restaurantAux);

        try {
            restaurantAux = restaurantRegisterService.add(restaurantAux);

            return genericAssembler.toClass(restaurantAux, RestaurantModel.class);
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
    public RestaurantModel partialUpdate(@PathVariable Long id, @RequestBody Map<String, Object> fields, HttpServletRequest request) {
        Restaurant restaurantAux = restaurantRegisterService.findOrThrow(id);

        merge(fields, restaurantAux, request);
        validate(restaurantAux, "restaurant");

        RestaurantInput restaurantInput = new RestaurantInput();
        restaurantInput.setName(restaurantAux.getName());
        restaurantInput.setDeliveryFee(restaurantAux.getDeliveryFee());

        KitchenIdInput kitchenIdInput = new KitchenIdInput();
        kitchenIdInput.setId(restaurantAux.getKitchen().getId());

        restaurantInput.setKitchen(kitchenIdInput);

        return update(id, restaurantInput);
    }

    @PutMapping("/{id}/active")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void activate(@PathVariable Long id) {
        restaurantRegisterService.activate(id);
    }

    @DeleteMapping("/{id}/active")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deactivate(@PathVariable Long id) {
        restaurantRegisterService.deactivate(id);
    }

    private void validate(Restaurant restaurantAux, String objectName) {
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restaurantAux, objectName);
        validator.validate(restaurantAux, bindingResult);

        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }
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
