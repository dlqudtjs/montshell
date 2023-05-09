package com.lbs.montshell.services.userServices;

import com.lbs.montshell.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> findByUsername(String username);
}
