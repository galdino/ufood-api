package com.galdino.ufood.api.v2.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class KitchenInputV2 {

    @ApiModelProperty(example = "Italian", required = true)
    @NotBlank
    private String kitchenName;

}
