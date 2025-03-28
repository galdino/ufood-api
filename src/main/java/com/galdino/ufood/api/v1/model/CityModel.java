package com.galdino.ufood.api.v1.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CityModel {
    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Los Angeles")
    private String name;

    private StateModel state;
}