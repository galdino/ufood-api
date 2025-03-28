package com.galdino.ufood.api.v2.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CityModelV2 {
    @ApiModelProperty(example = "1")
    private Long cityId;

    @ApiModelProperty(example = "Los Angeles")
    private String cityName;

    private Long stateId;
    private String stateName;
}