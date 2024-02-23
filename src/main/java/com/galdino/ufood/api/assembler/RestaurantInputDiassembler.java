package com.galdino.ufood.api.assembler;

import com.galdino.ufood.api.model.RestaurantInput;
import com.galdino.ufood.domain.model.Restaurant;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class RestaurantInputDiassembler {

    private ModelMapper modelMapper;

    public RestaurantInputDiassembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Restaurant toDomainObject(RestaurantInput restaurantInput) {

        return modelMapper.map(restaurantInput, Restaurant.class);

    }

}
