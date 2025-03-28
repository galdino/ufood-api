package com.galdino.ufood.api.v1.assembler;

import com.galdino.ufood.api.v1.model.RestaurantModel;
import com.galdino.ufood.domain.model.Restaurant;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RestaurantModelAssembler {

    private ModelMapper modelMapper;

    public RestaurantModelAssembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public RestaurantModel toModel(Restaurant restaurant) {
        return modelMapper.map(restaurant, RestaurantModel.class);
    }

    public List<RestaurantModel> toCollectionModel(List<Restaurant> restaurants) {
        return restaurants.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

}
