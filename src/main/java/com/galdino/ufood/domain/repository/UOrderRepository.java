package com.galdino.ufood.domain.repository;

import com.galdino.ufood.domain.model.UOrder;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UOrderRepository extends CustomJpaRepository<UOrder, Long>, JpaSpecificationExecutor<UOrder> {

    @Query("from UOrder u join fetch u.user join fetch u.restaurant r join fetch r.kitchen")
    List<UOrder> findAll();

    Optional<UOrder> findByCode(String code);

}
