package com.lbs.montshell.services.userServices;

import com.lbs.montshell.models.User;

public interface LoginService {

    User login(User user);

    void checkUsername(String username);
    void checkPassword(Long id, String password);
}
