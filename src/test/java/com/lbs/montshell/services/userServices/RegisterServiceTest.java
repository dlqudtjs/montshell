package com.lbs.montshell.services.userServices;

import com.lbs.montshell.models.User;
import com.lbs.montshell.repositories.JpaUserRepository;
import com.lbs.montshell.services.userServices.RegisterService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class RegisterServiceTest {

    @Autowired
    private RegisterService registerService;

    @Autowired
    private JpaUserRepository jpaUserRepository;

    @Test
    @DisplayName("회원가입")
    void join() {
        // given
        User user = new User();
        user.setUsername("test_name");
        user.setPassword("1234");
        
        // when
        User savedUser = registerService.join(user);

        // then
        assertThat(savedUser).isEqualTo(jpaUserRepository.findById(user.getId()).get());
    }

    @Test
    @DisplayName("중복된 아이디 검사")
    void DuplicateNameCheck() {
        // given
        User user1 = new User();
        user1.setUsername("duplicate_name");
        user1.setPassword("1234");

        User user2 = new User();
        user2.setUsername("duplicate_name");
        user2.setPassword("1234");

        // when
        registerService.join(user1);
        IllegalStateException e = Assertions.assertThrows(IllegalStateException.class, () -> {
            registerService.join(user2);
        });

        // then
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 아이디입니다.");
    }

    @Test
    @DisplayName("데이터베이스 출력")
    void printDatabase() {
        List<User> all = jpaUserRepository.findAll();
        for (User user : all) {
            System.out.println("user = " + user.getUsername());
        }
    }
}
