package com.galdino.ufood.domain.repository;

import com.galdino.ufood.domain.model.UGroup;
import org.springframework.stereotype.Repository;

@Repository
public interface UGroupRepository extends CustomJpaRepository<UGroup, Long> {
}
