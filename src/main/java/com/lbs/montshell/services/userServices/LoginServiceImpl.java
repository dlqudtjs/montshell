package com.lbs.montshell.services.userServices;

import com.lbs.montshell.models.User;
import com.lbs.montshell.repositories.JpaUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.NameNotFoundException;
import java.util.Optional;

@Service
@Transactional
public class LoginServiceImpl implements LoginService {

    private final JpaUserRepository userRepository;

    public LoginServiceImpl(JpaUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void checkUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 아이디입니다.");
        }
    }
}
