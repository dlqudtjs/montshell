package com.lbs.montshell.repositories;

import com.lbs.montshell.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaUserRepository extends JpaRepository<User, Long> {

    User save(User user);

    Optional<List<User>> findByUsername(String username);

    Optional<User> findById(Long id);

    Optional<List<User>> findByEmail(String email);

    List<User> findAll();


//    User save(User user);
//    User findById(Long id);
//    User findByName(String name);
//    User findByEmail(String email);
//    User findAll();
}
