package com.galdino.ufood.domain.repository;

import com.galdino.ufood.domain.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    List<Restaurant> findByDeliveryFeeBetween(BigDecimal initialFee, BigDecimal finalFee);
    List<Restaurant> findByNameContainingAndKitchenId(String name, Long kitchenId);
}
