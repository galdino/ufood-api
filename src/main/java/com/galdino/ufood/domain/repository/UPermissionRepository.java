package com.galdino.ufood.domain.repository;

import com.galdino.ufood.domain.model.UPermission;
import org.springframework.stereotype.Repository;

@Repository
public interface UPermissionRepository extends CustomJpaRepository<UPermission, Long> {
}
