package com.galdino.ufood.api.assembler;

import com.galdino.ufood.domain.model.City;
import com.galdino.ufood.domain.model.Kitchen;
import com.galdino.ufood.domain.model.Restaurant;
import com.galdino.ufood.domain.model.State;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GenericAssembler {

    private ModelMapper modelMapper;

    public GenericAssembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public <T> T toClass(Object object, Class<T> type) {
        return modelMapper.map(object, type);
    }

    public <T> List<T> toCollection(List<?> list, Class<T> type) {
        return list.stream()
                   .map(e -> toClass(e, type))
                   .collect(Collectors.toList());
    }

    public void copyToObject(Object fromObject, Object toObject) {

        if (fromObject == null || toObject == null) {
            return;
        }

        /*
         To avoid org.hibernate.HibernateException: identifier of an instance of
         com.galdino.ufood.domain.model.Kitchen was altered from 1 to 2
         */
        if (toObject instanceof Restaurant) {
            Restaurant restaurant = (Restaurant) toObject;
            restaurant.setKitchen(new Kitchen());

            if (restaurant.getAddress() != null) {
                restaurant.getAddress().setCity(new City());
            }
        }

        if (toObject instanceof City) {
            City city = (City) toObject;
            city.setState(new State());
        }

        modelMapper.map(fromObject, toObject);
    }


}
