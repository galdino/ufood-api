package com.galdino.ufood.api.v1.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class UOrderInput {

    @NotNull
    @Valid
    private RestaurantIdInput restaurant;

    @NotNull
    @Valid
    private PaymentMethodIdInput paymentMethod;

    @NotNull
    @Valid
    private AddressInput address;

    @NotNull
    @Valid
    @Size(min = 1)
    private List<UOrderItemInput> items;

}
