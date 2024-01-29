package com.galdino.ufood.core.validation.jackson;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.galdino.ufood.api.model.mixin.CityMixin;
import com.galdino.ufood.api.model.mixin.KitchenMixin;
import com.galdino.ufood.api.model.mixin.RestaurantMixin;
import com.galdino.ufood.domain.model.City;
import com.galdino.ufood.domain.model.Kitchen;
import com.galdino.ufood.domain.model.Restaurant;
import org.springframework.stereotype.Component;

@Component
public class JacksonMixinModule extends SimpleModule {
    public JacksonMixinModule() {
        setMixInAnnotation(Restaurant.class, RestaurantMixin.class);
        setMixInAnnotation(City.class, CityMixin.class);
        setMixInAnnotation(Kitchen.class, KitchenMixin.class);
    }
}
