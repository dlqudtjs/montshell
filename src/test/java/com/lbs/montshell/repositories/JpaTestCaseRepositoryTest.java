package com.lbs.montshell.repositories;

import com.lbs.montshell.models.TestCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class JpaTestCaseRepositoryTest {

    @Autowired
    JpaTestCaseRepository jpaTestCaseRepository;

    @Test
    @DisplayName("id 로 input 찾기")
    void findInputById() {
        String input = jpaTestCaseRepository.findInputById(1L);
        System.out.println(input);
    }

    @Test
    @DisplayName("id 로 output 찾기")
    void findOutputById() {
        String output = jpaTestCaseRepository.findOutputById(1L);
        System.out.println(output);
    }

    @Test
    @DisplayName("id 로 TestCase 찾기")
    void findById() {
        Optional<TestCase> testCase = jpaTestCaseRepository.findById(1L);
        System.out.println(testCase);
    }
}
