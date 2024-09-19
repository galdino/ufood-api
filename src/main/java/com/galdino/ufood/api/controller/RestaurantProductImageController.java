package com.galdino.ufood.api.controller;

import com.galdino.ufood.api.assembler.GenericAssembler;
import com.galdino.ufood.api.model.ProductImageInput;
import com.galdino.ufood.api.model.ProductImageModel;
import com.galdino.ufood.domain.exception.ProductNotFoundException;
import com.galdino.ufood.domain.model.Product;
import com.galdino.ufood.domain.model.ProductImage;
import com.galdino.ufood.domain.service.ProductImageService;
import com.galdino.ufood.domain.service.ProductRegisterService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping(value = "/restaurants/{rId}/products/{pId}/image")
public class RestaurantProductImageController {

    private final ProductRegisterService productRegisterService;
    private final ProductImageService productImageService;
    private final GenericAssembler genericAssembler;

    public RestaurantProductImageController(ProductRegisterService productRegisterService, ProductImageService productImageService, GenericAssembler genericAssembler) {
        this.productRegisterService = productRegisterService;
        this.productImageService = productImageService;
        this.genericAssembler = genericAssembler;
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ProductImageModel uploadImage(@PathVariable Long rId, @PathVariable Long pId, @Valid ProductImageInput productImageInput) throws IOException {
        Product product = productRegisterService.findOrThrow(rId, pId);

        ProductImage productImageAux = ProductImage.builder()
                                                   .product(product)
                                                   .description(productImageInput.getDescription())
                                                   .contentType(productImageInput.getFile().getContentType())
                                                   .size(productImageInput.getFile().getSize())
                                                   .fileName(productImageInput.getFile().getOriginalFilename())
                                                   .build();

        ProductImage productImage = productImageService.add(productImageAux, productImageInput.getFile().getInputStream());

        return genericAssembler.toClass(productImage, ProductImageModel.class);
    }

    @GetMapping
    public ProductImageModel findProductImage(@PathVariable Long rId, @PathVariable Long pId) {
        Optional<ProductImage> optionalProductImage = productImageService.getOptionalProductImage(rId, pId);

        if (optionalProductImage.isEmpty()) {
            throw new ProductNotFoundException(
                    String.format("Unable to find a image for product with id %d in restaurant with id %d", pId, rId));
        }

        return genericAssembler.toClass(optionalProductImage.get(), ProductImageModel.class);

    }

}
