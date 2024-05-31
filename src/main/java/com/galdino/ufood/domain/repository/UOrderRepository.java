package com.galdino.ufood.domain.repository;

import com.galdino.ufood.domain.model.UOrder;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UOrderRepository extends CustomJpaRepository<UOrder, Long> {

    @Query("from UOrder u join fetch u.user join fetch u.restaurant r join fetch r.kitchen")
    List<UOrder> findAll();

}
