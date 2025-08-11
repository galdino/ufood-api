package com.galdino.ufood.api.v1.controller;

import com.galdino.ufood.api.v1.assembler.GenericAssembler;
import com.galdino.ufood.api.v1.model.PaymentMethodModel;
import com.galdino.ufood.core.validation.security.CheckSecurity;
import com.galdino.ufood.domain.model.Restaurant;
import com.galdino.ufood.domain.service.RestaurantRegisterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/v1/restaurants/{rId}/payment-methods", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantPaymentMethodController {

    private final RestaurantRegisterService restaurantRegisterService;
    private final GenericAssembler genericAssembler;

    public RestaurantPaymentMethodController(RestaurantRegisterService restaurantRegisterService, GenericAssembler genericAssembler) {
        this.restaurantRegisterService = restaurantRegisterService;
        this.genericAssembler = genericAssembler;
    }

    @CheckSecurity.Restaurant.CanCheck
    @GetMapping
    public List<PaymentMethodModel> list(@PathVariable Long rId) {
        Restaurant restaurant = restaurantRegisterService.findOrThrow(rId);
        return genericAssembler.toCollection(restaurant.getPaymentMethods(), PaymentMethodModel.class);
    }

    @CheckSecurity.Restaurant.CanManageOpenClose
    @DeleteMapping("/{pId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void detach(@PathVariable Long rId, @PathVariable Long pId) {
        restaurantRegisterService.detachPaymentMethod(rId, pId);
    }

    @CheckSecurity.Restaurant.CanManageOpenClose
    @PutMapping("/{pId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void attach(@PathVariable Long rId, @PathVariable Long pId) {
        restaurantRegisterService.attachPaymentMethod(rId, pId);
    }

}
