package com.hrithik.splitwise.repository;

import com.hrithik.splitwise.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsernameLike(String username);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}
