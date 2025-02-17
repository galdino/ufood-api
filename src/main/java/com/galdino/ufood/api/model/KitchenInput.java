package com.galdino.ufood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class KitchenInput {

    @ApiModelProperty(example = "Italian", required = true)
    @NotBlank
    private String name;

}
