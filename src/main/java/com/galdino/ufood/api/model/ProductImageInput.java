package com.galdino.ufood.api.model;

import com.galdino.ufood.core.validation.FileSize;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ProductImageInput {

    @NotNull
    @FileSize(max = "500KB")
    private MultipartFile file;
    @NotBlank
    private String description;

}
