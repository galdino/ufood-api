package com.galdino.ufood.api.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.galdino.ufood.api.model.view.RestaurantView;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KitchenModel {
    @JsonView(RestaurantView.Summary.class)
    private Long id;
    @JsonView(RestaurantView.Summary.class)
    private String name;
}
