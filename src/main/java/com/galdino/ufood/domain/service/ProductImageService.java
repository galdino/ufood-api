package com.galdino.ufood.domain.service;

import com.galdino.ufood.domain.model.ProductImage;
import com.galdino.ufood.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProductImageService {

    public final ProductRepository productRepository;

    public ProductImageService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public ProductImage add(ProductImage productImage) {

        Long restaurantId = productImage.getProduct().getRestaurant().getId();
        Long productId = productImage.getProduct().getId();

        Optional<ProductImage> productImageAux = productRepository.findProductImageById(restaurantId, productId);
        if (productImageAux.isPresent()) {
            productRepository.delete(productImageAux.get());
        }

        return productRepository.save(productImage);
    }

}
