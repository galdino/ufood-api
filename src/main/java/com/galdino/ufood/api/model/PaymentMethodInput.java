package com.galdino.ufood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class PaymentMethodInput {

    @ApiModelProperty(example = "Cash", required = true)
    @NotBlank
    private String description;
}
