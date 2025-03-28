package com.galdino.ufood.api.v1.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PaymentMethodIdInput {

    @NotNull
    private Long id;
}
