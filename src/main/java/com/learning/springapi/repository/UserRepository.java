package com.learning.springapi.repository;

import com.learning.springapi.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsByEmail(String email);

    Page<User> findByAgeGreaterThanEqual(Integer age, Pageable pageable);

    Page<User> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<User> findByEmailContainingIgnoreCase(String email, Pageable pageable);

    Page<User> findByAgeGreaterThanEqualAndNameContainingIgnoreCase(Integer age, String name, Pageable pageable);

    Page<User> findByAgeGreaterThanEqualAndEmailContainingIgnoreCase(Integer age, String email, Pageable pageable);

    Page<User> findByNameContainingIgnoreCaseAndEmailContainingIgnoreCase(String name, String email, Pageable pageable);

    Page<User> findByAgeGreaterThanEqualAndNameContainingIgnoreCaseAndEmailContainingIgnoreCase(
            Integer age, String name, String email, Pageable pageable
    );

}




