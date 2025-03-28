package com.galdino.ufood.api.v1.controller;

import com.galdino.ufood.api.v1.assembler.GenericAssembler;
import com.galdino.ufood.api.v1.model.PaymentMethodModel;
import com.galdino.ufood.domain.model.Restaurant;
import com.galdino.ufood.domain.service.RestaurantRegisterService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/restaurants/{rId}/payment-methods")
public class RestaurantPaymentMethodController {

    private final RestaurantRegisterService restaurantRegisterService;
    private final GenericAssembler genericAssembler;

    public RestaurantPaymentMethodController(RestaurantRegisterService restaurantRegisterService, GenericAssembler genericAssembler) {
        this.restaurantRegisterService = restaurantRegisterService;
        this.genericAssembler = genericAssembler;
    }

    @GetMapping
    public List<PaymentMethodModel> list(@PathVariable Long rId) {
        Restaurant restaurant = restaurantRegisterService.findOrThrow(rId);
        return genericAssembler.toCollection(restaurant.getPaymentMethods(), PaymentMethodModel.class);
    }

    @DeleteMapping("/{pId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void detach(@PathVariable Long rId, @PathVariable Long pId) {
        restaurantRegisterService.detachPaymentMethod(rId, pId);
    }

    @PutMapping("/{pId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void attach(@PathVariable Long rId, @PathVariable Long pId) {
        restaurantRegisterService.attachPaymentMethod(rId, pId);
    }

}
