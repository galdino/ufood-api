package com.galdino.ufood.domain.service;

import com.galdino.ufood.domain.model.ProductImage;
import com.galdino.ufood.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductImageService {

    public final ProductRepository productRepository;

    public ProductImageService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public ProductImage add(ProductImage productImage) {
        return productRepository.save(productImage);
    }

}
