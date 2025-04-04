package com.galdino.ufood.api.v1.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class StateIdInput {
    @ApiModelProperty(example = "1", required = true)
    @NotNull
    private Long id;
}
