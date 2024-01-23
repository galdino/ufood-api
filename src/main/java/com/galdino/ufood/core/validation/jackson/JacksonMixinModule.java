package com.galdino.ufood.core.validation.jackson;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.galdino.ufood.api.model.mixin.RestaurantMixin;
import com.galdino.ufood.domain.model.Restaurant;
import org.springframework.stereotype.Component;

@Component
public class JacksonMixinModule extends SimpleModule {
    public JacksonMixinModule() {
        setMixInAnnotation(Restaurant.class, RestaurantMixin.class);
    }
}
