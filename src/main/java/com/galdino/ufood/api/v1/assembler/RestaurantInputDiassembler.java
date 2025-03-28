package com.galdino.ufood.api.v1.assembler;

import com.galdino.ufood.api.v1.model.RestaurantInput;
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

    public void copyToDomainObject(RestaurantInput restaurantInput, Restaurant restaurant) {

        //To avoid org.hibernate.HibernateException: identifier of an instance of
        //com.galdino.ufood.domain.model.Kitchen was altered from 1 to 2
//        restaurant.setKitchen(new Kitchen());

        modelMapper.map(restaurantInput, restaurant);

    }

}
