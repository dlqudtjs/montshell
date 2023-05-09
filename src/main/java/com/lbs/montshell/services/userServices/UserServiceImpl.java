package com.lbs.montshell.services.userServices;

import com.lbs.montshell.models.User;
import com.lbs.montshell.repositories.JpaUserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    private final JpaUserRepository userRepository;

    public UserServiceImpl(JpaUserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
