package com.galdino.ufood.api.v1.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class StateInput {

    @NotBlank
    private String name;
}
