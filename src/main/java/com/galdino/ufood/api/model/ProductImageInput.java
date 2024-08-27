package com.galdino.ufood.api.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ProductImageInput {

    private MultipartFile file;
    private String description;

}
