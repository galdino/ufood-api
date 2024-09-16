package com.galdino.ufood.domain.service;

import com.galdino.ufood.domain.model.ProductImage;
import com.galdino.ufood.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Optional;

import static com.galdino.ufood.domain.service.ImageStorageService.NewImage;

@Service
public class ProductImageService {

    public final ProductRepository productRepository;
    public final ImageStorageService imageStorageService;

    public ProductImageService(ProductRepository productRepository, ImageStorageService imageStorageService) {
        this.productRepository = productRepository;
        this.imageStorageService = imageStorageService;
    }

    @Transactional
    public ProductImage add(ProductImage productImage, InputStream fileData) {

        Long restaurantId = productImage.getProduct().getRestaurant().getId();
        Long productId = productImage.getProduct().getId();
        String originalFileName = productImage.getFileName();
        String isPresentFileName = null;

        Optional<ProductImage> productImageAux = productRepository.findProductImageById(restaurantId, productId);
        if (productImageAux.isPresent()) {
            isPresentFileName = productImageAux.get().getFileName();
            productRepository.delete(productImageAux.get());
        }

        productImage.setFileName(imageStorageService.createFileName(originalFileName));

        productImage = productRepository.save(productImage);
        productRepository.flush();

        NewImage newImage = NewImage.builder()
                                    .fileName(productImage.getFileName())
                                    .inputStream(fileData)
                                    .build();

        imageStorageService.replace(isPresentFileName, newImage);

        return productImage;
    }

}
