package com.galdino.ufood.api.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CityInput {

    @NotBlank
    private String name;

    @Valid
    @NotNull
    private StateIdInput state;
}
