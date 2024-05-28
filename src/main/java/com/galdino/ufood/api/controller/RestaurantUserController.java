package com.galdino.ufood.api.controller;

import com.galdino.ufood.api.assembler.GenericAssembler;
import com.galdino.ufood.api.model.UserModel;
import com.galdino.ufood.domain.model.Restaurant;
import com.galdino.ufood.domain.service.RestaurantRegisterService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurants/{rId}/admins")
public class RestaurantUserController {

    private final RestaurantRegisterService restaurantRegisterService;
    private final GenericAssembler genericAssembler;

    public RestaurantUserController(RestaurantRegisterService restaurantRegisterService, GenericAssembler genericAssembler) {
        this.restaurantRegisterService = restaurantRegisterService;
        this.genericAssembler = genericAssembler;
    }

    @GetMapping
    public List<UserModel> list(@PathVariable Long rId) {
        Restaurant restaurant = restaurantRegisterService.findOrThrow(rId);
        return genericAssembler.toCollection(restaurant.getUsers(), UserModel.class);
    }

    @DeleteMapping("/{uId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void detach(@PathVariable Long rId, @PathVariable Long uId) {
        restaurantRegisterService.detachUser(rId, uId);
    }

    @PutMapping("/{uId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void attach(@PathVariable Long rId, @PathVariable Long uId) {
        restaurantRegisterService.attachUser(rId, uId);
    }

}
