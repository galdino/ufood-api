package com.galdino.ufood.api.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RestaurantIdInput {

    @NotNull
    private Long id;

}
