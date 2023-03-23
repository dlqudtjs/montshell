package com.lbs.montshell.services.userServices;

import com.lbs.montshell.models.User;
import com.lbs.montshell.repositories.JpaUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RegisterServiceImpl implements RegisterService{

    private final JpaUserRepository userRepository;

    public RegisterServiceImpl(JpaUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User join(User user) {
        checkDuplicate(user);
        return userRepository.save(user);
    }

    private void checkDuplicate(User user) {
        userRepository.findByUsername(user.getUsername())
                .ifPresent(findUser -> {
                    throw new IllegalStateException("이미 존재하는 아이디입니다.");
                });

        userRepository.findByEmail(user.getEmail())
                .ifPresent(findUser -> {
                    throw new IllegalStateException("이미 존재하는 이메일입니다.");
                });
    }
}
