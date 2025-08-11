package com.galdino.ufood.api.v1.controller;

import com.galdino.ufood.api.v1.assembler.GenericAssembler;
import com.galdino.ufood.api.v1.model.UserModel;
import com.galdino.ufood.core.validation.security.CheckSecurity;
import com.galdino.ufood.domain.model.Restaurant;
import com.galdino.ufood.domain.service.RestaurantRegisterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/v1/restaurants/{rId}/admins", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantUserController {

    private final RestaurantRegisterService restaurantRegisterService;
    private final GenericAssembler genericAssembler;

    public RestaurantUserController(RestaurantRegisterService restaurantRegisterService, GenericAssembler genericAssembler) {
        this.restaurantRegisterService = restaurantRegisterService;
        this.genericAssembler = genericAssembler;
    }

    @CheckSecurity.Restaurant.CanManageRegister
    @GetMapping
    public List<UserModel> list(@PathVariable Long rId) {
        Restaurant restaurant = restaurantRegisterService.findOrThrow(rId);
        return genericAssembler.toCollection(restaurant.getUsers(), UserModel.class);
    }

    @CheckSecurity.Restaurant.CanManageRegister
    @DeleteMapping("/{uId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void detach(@PathVariable Long rId, @PathVariable Long uId) {
        restaurantRegisterService.detachUser(rId, uId);
    }

    @CheckSecurity.Restaurant.CanManageRegister
    @PutMapping("/{uId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void attach(@PathVariable Long rId, @PathVariable Long uId) {
        restaurantRegisterService.attachUser(rId, uId);
    }

}
