package com.galdino.ufood.api.model.mixin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.galdino.ufood.domain.model.State;

public class CityMixin {
    @JsonIgnoreProperties(value = "name", allowGetters = true)
    private State state;
}
