package com.galdino.ufood.api.v1.model.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.galdino.ufood.domain.model.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class KitchenMixin {
    @JsonIgnore
    private List<Restaurant> restaurants = new ArrayList<>();
}
