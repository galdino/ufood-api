package com.galdino.ufood.api.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class StateIdInput {
    @NotNull
    private Long id;
}
