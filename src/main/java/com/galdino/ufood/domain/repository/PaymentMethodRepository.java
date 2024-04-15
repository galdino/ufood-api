package com.galdino.ufood.domain.repository;

import com.galdino.ufood.domain.model.PaymentMethod;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentMethodRepository extends CustomJpaRepository<PaymentMethod, Long> {
}
