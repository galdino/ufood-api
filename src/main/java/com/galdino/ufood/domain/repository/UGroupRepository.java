package com.galdino.ufood.domain.repository;

import com.galdino.ufood.domain.model.UGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UGroupRepository extends JpaRepository<UGroup, Long> {
}
