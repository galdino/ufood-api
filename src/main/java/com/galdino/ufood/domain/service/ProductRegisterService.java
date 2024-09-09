package com.galdino.ufood.domain.service;

import com.galdino.ufood.domain.exception.ProductNotFoundException;
import com.galdino.ufood.domain.model.Product;
import com.galdino.ufood.domain.model.Restaurant;
import com.galdino.ufood.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductRegisterService {

    private final ProductRepository productRepository;
    private final RestaurantRegisterService restaurantRegisterService;
    public ProductRegisterService(ProductRepository productRepository, RestaurantRegisterService restaurantRegisterService) {
        this.productRepository = productRepository;
        this.restaurantRegisterService = restaurantRegisterService;
    }

    public Product findOrThrow(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    }

    public Product findOrThrow(Long restaurantId, Long productId) {
        Restaurant restaurant = restaurantRegisterService.findOrThrow(restaurantId);

        Product productAux = new Product();
        productAux.setId(productId);

        return restaurant.existsProduct(productAux);
    }

    @Transactional
    public Product add(Restaurant restaurant, Product product) {
        product.setRestaurant(restaurant);

        return productRepository.save(product);
    }
}
