package com.lbs.montshell.repositories;

import com.lbs.montshell.models.SubmitInfo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JpaSubmitInfoRepositoryTest {

    @Autowired
    JpaSubmitInfoRepository jpaSubmitInfoRepository;

    @Test
    @DisplayName("SubmitInfo 저장")
    void save() {
        // given
        SubmitInfo submitInfo = new SubmitInfo();
        submitInfo.setUserId("testUser");
        submitInfo.setProblemId(2L);
        submitInfo.setLanguage("java");
        submitInfo.setCorrect("Correct");
        submitInfo.setUser_code("test code");
        submitInfo.setMemory_usage("1000");
        submitInfo.setExecution_time("1000");

        // when
        SubmitInfo savedSubmitInfo = jpaSubmitInfoRepository.save(submitInfo);

        // then
        Assertions.assertThat(savedSubmitInfo.getId()).isNotNull();
        Assertions.assertThat(savedSubmitInfo.getUserId()).isEqualTo(submitInfo.getUserId());
    }
}
