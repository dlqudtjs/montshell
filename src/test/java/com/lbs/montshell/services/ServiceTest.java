package com.lbs.montshell.services;

import com.lbs.montshell.models.User;
import com.lbs.montshell.services.userServices.RegisterService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class ServiceTest {

    @Autowired
    RegisterService registerService;

    @Test
    @DisplayName("회원가입")
    void join() {
        // given
        User user = new User();
        user.setUsername("name");
        user.setEmail("mail");
        user.setPassword("1234");
        
        // when
        User savedUser = registerService.join(user);

        // then
        assertThat(savedUser.getId()).isEqualTo(user.getId());
    }

    @Test
    @DisplayName("중복된 아이디 검사")
    void DuplicateNameCheck() {
        // given
        User user1 = new User();
        user1.setUsername("duplicate_name");
        user1.setEmail("mail1");
        user1.setPassword("1234");

        User user2 = new User();
        user2.setUsername("duplicate_name");
        user2.setEmail("mail2");
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
    @DisplayName("중복된 이메일 검사")
    void DuplicateMailCheck() {
        // given
        User user1 = new User();
        user1.setUsername("name1");
        user1.setEmail("duplicate_mail");
        user1.setPassword("1234");

        User user2 = new User();
        user2.setUsername("name2");
        user2.setEmail("duplicate_mail");
        user2.setPassword("1234");

        // when
        registerService.join(user1);
        IllegalStateException e = Assertions.assertThrows(IllegalStateException.class, () -> {
            registerService.join(user2);
        });

        // then
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 이메일입니다.");
    }
}
