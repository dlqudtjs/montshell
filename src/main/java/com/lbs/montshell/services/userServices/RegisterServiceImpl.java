package com.lbs.montshell.services.userServices;

import com.lbs.montshell.models.User;
import com.lbs.montshell.repositories.JpaUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RegisterServiceImpl implements RegisterService{

    private final JpaUserRepository userRepository;

    public RegisterServiceImpl(JpaUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 이상이 없다면 Repository 에 저장한다.
    @Override
    public User join(User user) {
        checkDuplicate(user);
        return userRepository.save(user);
    }

    // 사용자가 입력한 정보가 중복되는지 확인한다.
    private void checkDuplicate(User user) {
        userRepository.findByUsername(user.getUsername())
                .ifPresent(findUser -> {
                    throw new IllegalStateException("이미 존재하는 아이디입니다.");
                });
    }
}
