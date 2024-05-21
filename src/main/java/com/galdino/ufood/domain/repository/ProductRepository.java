package com.galdino.ufood.domain.repository;

import com.galdino.ufood.domain.model.Product;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CustomJpaRepository<Product, Long> {
}
