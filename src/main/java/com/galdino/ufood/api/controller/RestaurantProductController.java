package com.galdino.ufood.api.controller;

import com.galdino.ufood.api.assembler.GenericAssembler;
import com.galdino.ufood.api.model.ProductInput;
import com.galdino.ufood.api.model.ProductModel;
import com.galdino.ufood.domain.model.Product;
import com.galdino.ufood.domain.model.Restaurant;
import com.galdino.ufood.domain.service.ProductRegisterService;
import com.galdino.ufood.domain.service.RestaurantRegisterService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/restaurants/{rId}/products")
public class RestaurantProductController {

    private final RestaurantRegisterService restaurantRegisterService;
    private final ProductRegisterService productRegisterService;
    private final GenericAssembler genericAssembler;

    public RestaurantProductController(RestaurantRegisterService restaurantRegisterService, ProductRegisterService productRegisterService, GenericAssembler genericAssembler) {
        this.restaurantRegisterService = restaurantRegisterService;
        this.productRegisterService = productRegisterService;
        this.genericAssembler = genericAssembler;
    }

    @GetMapping
    public List<ProductModel> list(@PathVariable Long rId) {
        Restaurant restaurant = restaurantRegisterService.findOrThrow(rId);
        return genericAssembler.toCollection(restaurant.getProducts(), ProductModel.class);
    }

    @GetMapping("/{pId}")
    public ProductModel restaurantProduct(@PathVariable Long rId, @PathVariable Long pId) {
        Restaurant restaurant = restaurantRegisterService.findOrThrow(rId);
        Product product = productRegisterService.findOrThrow(pId);

        Product productAux = restaurant.existsProduct(product);

        return genericAssembler.toClass(productAux, ProductModel.class);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductModel add(@PathVariable Long rId, @RequestBody @Valid ProductInput productInput) {
        Restaurant restaurant = restaurantRegisterService.findOrThrow(rId);
        Product product = genericAssembler.toClass(productInput, Product.class);
        Product productAux = productRegisterService.add(restaurant, product);

        return genericAssembler.toClass(productAux, ProductModel.class);
    }

    @PutMapping("/{pId}")
    public ProductModel update(@PathVariable Long rId, @PathVariable Long pId, @RequestBody @Valid ProductInput productInput) {
        Restaurant restaurant = restaurantRegisterService.findOrThrow(rId);
        Product product = productRegisterService.findOrThrow(pId);

        Product productAux = restaurant.existsProduct(product);

        genericAssembler.copyToObject(productInput, productAux);

        productAux = productRegisterService.add(restaurant, productAux);

        return genericAssembler.toClass(productAux, ProductModel.class);
    }
}
