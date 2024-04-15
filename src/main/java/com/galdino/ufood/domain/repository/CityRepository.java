package com.galdino.ufood.domain.repository;

import com.galdino.ufood.domain.model.City;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends CustomJpaRepository<City, Long> {
}
