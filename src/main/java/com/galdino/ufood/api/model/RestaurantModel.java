package com.galdino.ufood.api.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RestaurantModel {
    private Long id;
    private String name;
    private BigDecimal deliveryFee;
    private KitchenModel kitchen;
    private Boolean active;
    private Boolean open;
    private AddressModel address;
}
