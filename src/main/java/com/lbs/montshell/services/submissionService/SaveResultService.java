package com.lbs.montshell.services.submissionService;

import com.lbs.montshell.controllers.SubmitForm;
import com.lbs.montshell.models.SubmitInfo;
import com.lbs.montshell.repositories.JpaSubmitInfoRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class SaveResultService {

    JpaSubmitInfoRepository jpaSubmitInfoRepository;

    public SaveResultService(JpaSubmitInfoRepository jpaSubmitInfoRepository) {
        this.jpaSubmitInfoRepository = jpaSubmitInfoRepository;
    }

    public void saveResult(SubmitForm submitForm, Path resultFile) throws IOException {
        String memoryUsage = "";
        String executionTime = "";
        String correct = "";

        // getFileContent
        List<String> lines = Files.readAllLines(resultFile);

        // getData
        for(String line : lines) {
            if(line.contains("Memory usage:")) {
                memoryUsage = line.substring(14);
            }
            if(line.contains("Execution time:")) {
                executionTime = line.substring(16);
            }
            if(line.contains("Correct:")) {
                correct = line.substring(9);
            }
        }

        SubmitInfo submitInfo = new SubmitInfo();
        submitInfo.setUserId(submitForm.getUser_id());
        submitInfo.setProblemId(submitForm.getProblem_id());
        submitInfo.setLanguage(submitForm.getLanguage());
        submitInfo.setCorrect(correct);
        submitInfo.setUser_code(submitForm.getCode());
        submitInfo.setMemory_usage(memoryUsage);
        submitInfo.setExecution_time(executionTime);

        // save
        jpaSubmitInfoRepository.save(submitInfo);
    }
}
