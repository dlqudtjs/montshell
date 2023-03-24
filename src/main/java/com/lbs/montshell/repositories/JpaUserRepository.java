package com.lbs.montshell.repositories;

import com.lbs.montshell.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaUserRepository extends JpaRepository<User, Long> {

    User save(User user);

    Optional<User> findByUsername(String username);

    Optional<User> findById(Long id);

    List<User> findAll();
}
