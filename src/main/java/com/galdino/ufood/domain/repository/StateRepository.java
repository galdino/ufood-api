package com.galdino.ufood.domain.repository;


import com.galdino.ufood.domain.model.State;
import org.springframework.stereotype.Repository;

@Repository
public interface StateRepository extends CustomJpaRepository<State, Long> {
}
