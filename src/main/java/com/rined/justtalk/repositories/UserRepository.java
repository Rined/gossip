package com.rined.justtalk.repositories;

import com.rined.justtalk.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    boolean existsByUsername(String username);

    Optional<User> findByActivationCode(String code);

    Page<User> findAll(Pageable pageable);

}
