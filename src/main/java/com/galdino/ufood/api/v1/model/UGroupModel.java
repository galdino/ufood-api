package com.galdino.ufood.api.v1.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UGroupModel {
    @ApiModelProperty(example = "1")
    private Long id;
    @ApiModelProperty(example = "Manager")
    private String name;
}
