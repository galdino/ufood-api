package com.galdino.ufood.api.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.galdino.ufood.api.model.view.RestaurantView;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RestaurantModel {
    @JsonView({RestaurantView.Summary.class, RestaurantView.NameOnly.class})
    private Long id;
    @JsonView({RestaurantView.Summary.class, RestaurantView.NameOnly.class})
    private String name;
    @JsonView(RestaurantView.Summary.class)
    private BigDecimal deliveryFee;
    @JsonView(RestaurantView.Summary.class)
    private KitchenModel kitchen;
    private Boolean active;
    private Boolean open;
    private AddressModel address;
}
