package com.galdino.ufood.api.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class PasswordUpdateInput {

    @NotBlank
    private String currentPassword;

    @NotBlank
    private String newPassword;
}
