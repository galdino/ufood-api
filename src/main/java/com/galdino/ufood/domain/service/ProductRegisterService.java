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
    public ProductRegisterService(ProductRepository productRepository, RestaurantRegisterService restaurantRegisterService) {
        this.productRepository = productRepository;
    }

    public Product findOrThrow(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    }

    @Transactional
    public Product add(Restaurant restaurant, Product product) {
        product.setRestaurant(restaurant);

        return productRepository.save(product);
    }
}
