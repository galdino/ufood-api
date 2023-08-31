package com.galdino.ufood.domain.repository;

import com.galdino.ufood.domain.model.Restaurant;

import java.util.List;

public interface RestaurantRepository {
    List<Restaurant> list();
    Restaurant findById(Long id);
    Restaurant add(Restaurant restaurant);
    void delete(Restaurant restaurant);
}
