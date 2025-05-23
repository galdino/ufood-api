package com.galdino.ufood.api.v1.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UserInputAux {
    @NotBlank
    private String name;

    @NotBlank
    @Email(message = "Email is invalid")
    private String email;
}
