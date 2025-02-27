package com.galdino.ufood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentMethodModel {
    @ApiModelProperty(example = "1", position = 0)
    private Long id;
    @ApiModelProperty(example = "Cash", position = 1)
    private String description;
}
