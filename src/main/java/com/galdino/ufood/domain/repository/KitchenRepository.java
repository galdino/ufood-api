package com.galdino.ufood.domain.repository;

import com.galdino.ufood.domain.model.Kitchen;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KitchenRepository extends CustomJpaRepository<Kitchen, Long> {
    List<Kitchen> findByNameContaining(String name);
    boolean existsByName(String name);
}
