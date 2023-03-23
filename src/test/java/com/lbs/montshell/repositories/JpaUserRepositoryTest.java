package com.lbs.montshell.repositories;

import com.lbs.montshell.models.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class JpaUserRepositoryTest {

    @Autowired
    private JpaUserRepository userRepository;

    @Test
    @DisplayName("회원 저장")
    void save() {
        // given
        User user = new User();
        user.setUsername("test_name");
        user.setEmail("test_mail");
        user.setPassword("1234");

        // when
        User savedUser = userRepository.save(user);

        // then
        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getUsername()).isEqualTo(user.getUsername());
        assertThat(savedUser.getEmail()).isEqualTo(user.getEmail());
        assertThat(savedUser.getPassword()).isEqualTo(user.getPassword());
    }
}
