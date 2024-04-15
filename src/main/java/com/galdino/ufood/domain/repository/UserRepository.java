package com.galdino.ufood.domain.repository;

import com.galdino.ufood.domain.model.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CustomJpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
