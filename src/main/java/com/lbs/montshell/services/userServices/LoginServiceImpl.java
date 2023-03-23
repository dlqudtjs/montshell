package com.lbs.montshell.services.userServices;

import com.lbs.montshell.models.User;
import com.lbs.montshell.repositories.JpaUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LoginServiceImpl implements LoginService{

    private final JpaUserRepository userRepository;

    public LoginServiceImpl(JpaUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User login(User user) {
        // todo 아이디, 비밀번호 확인 후 로그인 진행하는 메소드 만들기
        
        return userRepository.save(user);
    }
}
