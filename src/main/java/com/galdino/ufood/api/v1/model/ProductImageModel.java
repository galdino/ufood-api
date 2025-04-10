package com.galdino.ufood.api.v1.model;

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
