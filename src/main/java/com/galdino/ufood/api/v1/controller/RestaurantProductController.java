package com.galdino.ufood.api.v1.controller;

import com.galdino.ufood.api.v1.assembler.GenericAssembler;
import com.galdino.ufood.api.v1.model.ProductInput;
import com.galdino.ufood.api.v1.model.ProductModel;
import com.galdino.ufood.domain.model.Product;
import com.galdino.ufood.domain.model.Restaurant;
import com.galdino.ufood.domain.service.ProductRegisterService;
import com.galdino.ufood.domain.service.RestaurantRegisterService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<ProductModel> list(@PathVariable Long rId,
                                   @RequestParam(required = false) boolean addInactive) {
        Restaurant restaurant = restaurantRegisterService.findOrThrow(rId);

        List<Product> products;
        if (addInactive) {
            products = restaurant.getProducts();
        } else {
            products = restaurant.getProducts().stream()
                                               .filter(Product::getActive)
                                               .collect(Collectors.toList());
        }

        return genericAssembler.toCollection(products, ProductModel.class);
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
