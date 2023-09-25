package com.galdino.ufood.domain.repository;

import com.galdino.ufood.domain.model.City;

import java.util.List;

public interface CityRepository {
    List<City> list();
    City findById(Long id);
    City add(City city);
    void delete(Long id);
}
