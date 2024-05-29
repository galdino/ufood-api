package com.galdino.ufood.domain.repository;

import com.galdino.ufood.domain.model.UOrder;
import org.springframework.stereotype.Repository;

@Repository
public interface UOrderRepository extends CustomJpaRepository<UOrder, Long> {
}
