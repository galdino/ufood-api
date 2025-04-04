package com.galdino.ufood.api.v2.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CityInputV2 {

    @ApiModelProperty(example = "Los Angeles", required = true)
    @NotBlank
    private String cityName;

    @ApiModelProperty(example = "1", required = true)
    @NotNull
    private Long stateId;
}
