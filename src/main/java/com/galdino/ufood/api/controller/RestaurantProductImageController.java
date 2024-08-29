package com.galdino.ufood.api.controller;

import com.galdino.ufood.api.model.ProductImageInput;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.nio.file.Path;
import java.util.UUID;

@RestController
@RequestMapping(value = "/restaurants/{rId}/products/{pId}/image")
public class RestaurantProductImageController {

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadImage(@PathVariable Long rId, @PathVariable Long pId, @Valid ProductImageInput productImageInput) {
        String fileName = UUID.randomUUID() + "_" + productImageInput.getFile().getOriginalFilename();

        Path path = Path.of("/upload", fileName);

        System.out.println(productImageInput.getDescription());
        System.out.println(path);
        System.out.println(productImageInput.getFile().getContentType());

        try {
            productImageInput.getFile().transferTo(path);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
