package com.galdino.ufood.api.v1.controller;

import com.galdino.ufood.api.v1.assembler.GenericAssembler;
import com.galdino.ufood.api.v1.model.ProductImageInput;
import com.galdino.ufood.api.v1.model.ProductImageModel;
import com.galdino.ufood.domain.exception.ProductNotFoundException;
import com.galdino.ufood.domain.model.Product;
import com.galdino.ufood.domain.model.ProductImage;
import com.galdino.ufood.domain.service.ImageStorageService;
import com.galdino.ufood.domain.service.ImageStorageService.RecoveredImage;
import com.galdino.ufood.domain.service.ProductImageService;
import com.galdino.ufood.domain.service.ProductRegisterService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/v1/restaurants/{rId}/products/{pId}/image")
public class RestaurantProductImageController {

    private final ProductRegisterService productRegisterService;
    private final ProductImageService productImageService;
    private final ImageStorageService imageStorageService;
    private final GenericAssembler genericAssembler;

    public RestaurantProductImageController(ProductRegisterService productRegisterService, ProductImageService productImageService, ImageStorageService imageStorageService, GenericAssembler genericAssembler) {
        this.productRegisterService = productRegisterService;
        this.productImageService = productImageService;
        this.imageStorageService = imageStorageService;
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

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductImageModel findProductImage(@PathVariable Long rId, @PathVariable Long pId) {
        Optional<ProductImage> optionalProductImage = productImageService.getOptionalProductImage(rId, pId);

        if (optionalProductImage.isEmpty()) {
            throw new ProductNotFoundException(
                    String.format("Unable to find a image for product with id %d in restaurant with id %d", pId, rId));
        }

        return genericAssembler.toClass(optionalProductImage.get(), ProductImageModel.class);

    }

    @GetMapping
    public ResponseEntity<?> findImage(@PathVariable Long rId, @PathVariable Long pId,
                                                         @RequestHeader(name = "accept") String acceptHeader) throws HttpMediaTypeNotAcceptableException {
        List<MediaType> acceptMediaTypes = MediaType.parseMediaTypes(acceptHeader);

        Optional<ProductImage> optionalProductImage = productImageService.getOptionalProductImage(rId, pId);

        if (optionalProductImage.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        MediaType imageMediaType = MediaType.parseMediaType(optionalProductImage.get().getContentType());
        validateMediaType(acceptMediaTypes, imageMediaType);

        RecoveredImage recoveredImage = imageStorageService.recover(optionalProductImage.get().getFileName());

        if (recoveredImage.isUrl()) {
            return ResponseEntity.status(HttpStatus.FOUND)
                                 .header(HttpHeaders.LOCATION, recoveredImage.getUrl())
                                 .build();
        } else {
            return ResponseEntity.ok()
                                 .contentType(imageMediaType)
                                 .body(new InputStreamResource(recoveredImage.getInputStream()));
        }

    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProductImage(@PathVariable Long rId, @PathVariable Long pId) {
        Optional<ProductImage> optionalProductImage = productImageService.getOptionalProductImage(rId, pId);

        if (optionalProductImage.isEmpty()) {
            throw new ProductNotFoundException(
                    String.format("Unable to find a image for product with id %d in restaurant with id %d", pId, rId));
        }

        productImageService.remove(optionalProductImage.get());
    }

    private void validateMediaType(List<MediaType> acceptMediaTypes, MediaType imageMediaType) throws HttpMediaTypeNotAcceptableException {
        boolean anyMatchMediaType = acceptMediaTypes.stream()
                                                    .anyMatch(mt -> mt.isCompatibleWith(imageMediaType));

        if (!anyMatchMediaType) {
            throw new HttpMediaTypeNotAcceptableException(acceptMediaTypes);
        }
    }

}
