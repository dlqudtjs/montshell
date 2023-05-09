package com.lbs.montshell.services.userServices;

import com.lbs.montshell.models.SubmitInfo;
import com.lbs.montshell.repositories.JpaSubmitInfoRepository;
import com.lbs.montshell.services.submitService.SubmitService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

@SpringBootTest
public class UserServiceTest {
    
    @Autowired
    private SubmitService submitService;


    @Test
    @DisplayName("userId로 submitList 가져오기")
    public void testFindByUsername() {
        // given
        String user_id = "user";
        
        // when
        Page<SubmitInfo> submitInfoList = submitService.getSubmitInfoListByUserId(user_id, null);

        // then
        System.out.println("submitInfoListSize = " + submitInfoList.getSize());
    }
}
