package com.galdino.ufood.api.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UGroupInput {

    @NotBlank
    private String name;
}
