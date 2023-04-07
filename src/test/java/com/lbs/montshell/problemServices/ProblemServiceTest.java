package com.lbs.montshell.problemServices;

import com.lbs.montshell.models.Problem;
import com.lbs.montshell.repositories.JpaProblemRepository;
import com.lbs.montshell.services.problemServices.ProblemService;
import com.lbs.montshell.services.problemServices.ProblemServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.NoSuchElementException;

import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ProblemServiceTest {

    @Autowired
    private ProblemService problemService;

    @Test
    @DisplayName("문제 리스트 조회")
    void problemList() {
        // given
        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<Problem> pages = problemService.problemList(pageable);

        // then
        assertNotNull(pages);
        System.out.println("Total problems: " + pages.getTotalElements());
        pages.getContent().forEach(System.out::println);
    }

    @Test
    @DisplayName("문제 조회")
    void getProblem() {
        // given
        Long id = 1L;

        // when
        Problem problem = problemService.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Problem not found with Id = " + id));

        // then
        assertNotNull(problem);
        System.out.println(problem);
    }
}
