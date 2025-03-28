package com.galdino.ufood.api.v1.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StateModel {
    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "CA")
    private String name;
}
