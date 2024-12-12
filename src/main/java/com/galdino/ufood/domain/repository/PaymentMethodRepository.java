package com.galdino.ufood.domain.repository;

import com.galdino.ufood.domain.model.PaymentMethod;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;

@Repository
public interface PaymentMethodRepository extends CustomJpaRepository<PaymentMethod, Long> {
    @Query("select max(updateDate) from PaymentMethod")
    OffsetDateTime getMaxUpdateDate();
}
