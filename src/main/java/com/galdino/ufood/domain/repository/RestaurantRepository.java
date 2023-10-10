package com.galdino.ufood.domain.repository;

import com.galdino.ufood.domain.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long>, RestaurantRepositoryQueries {
    List<Restaurant> queryByDeliveryFeeBetween(BigDecimal initialFee, BigDecimal finalFee);
    List<Restaurant> findByNameContainingAndKitchenId(String name, Long kitchenId);
//    @Query("from Restaurant where name like %:name% and kitchen.id = :id")
    List<Restaurant> findByNameQuery(String name, @Param("id") Long kitchenId);
    Optional<Restaurant> findFirstRestaurantByNameContaining(String name);
    List<Restaurant> findTop2ByNameContaining(String name);
    int countByKitchenId(Long kitchenId);
}
