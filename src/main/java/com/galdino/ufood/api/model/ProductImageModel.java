package com.galdino.ufood.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductImageModel {

    private String fileName;
    private String description;
    private String contentType;
    private Long size;

}
