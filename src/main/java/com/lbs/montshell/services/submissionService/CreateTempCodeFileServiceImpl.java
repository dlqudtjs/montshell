package com.lbs.montshell.services.submissionService;

import com.lbs.montshell.controllers.SubmitForm;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class CreateTempCodeFileServiceImpl implements CreateTempCodeFileService {
    private final Path TEMP_CODE_FILE_PATH;

    public CreateTempCodeFileServiceImpl(@Qualifier("tempCodeFilePath") Path TEMP_CODE_FILE_PATH) {
        this.TEMP_CODE_FILE_PATH = TEMP_CODE_FILE_PATH;
    }

    @Override
    public String createTempCodeFile(SubmitForm submitForm) throws IOException {
        // code 파일 이름 생성 ex) java_1_1_
        String tempFileName = createTempCodeFileName(submitForm);

        // tempFileName 과 language 에 따라 codeFileName 생성 ex) java_1_1_Main.java
        String codeFileName = getCodeFileName(submitForm.getLanguage(), tempFileName);

        // TEMP_CODE_FILE_PATH 에 codeFileName 이름을 붙인다.
        Path codeFilePath = TEMP_CODE_FILE_PATH.resolve(codeFileName);

        // codeFilePath 에 submitForm.getCode() 를 저장한다.
        String shebang = "#!/usr/bin/env python3\n" + submitForm.getCode();
        Files.write(codeFilePath, shebang.getBytes());

        // codeFileName 을 리턴한다.
        return codeFileName;
    }

    public String createTempCodeFileName(SubmitForm submitForm) {
        return submitForm.getLanguage() + "_" + submitForm.getProblem_id() + "_" + submitForm.getUser_id() + "_";
    }

    public String getCodeFileName(String language, String tempFileName) {

        switch (language.toLowerCase()) {
            case "java":
                return tempFileName + "Main.java";
            case "python":
                return tempFileName + "Main.py";
            case "cpp":
                return tempFileName + "Main.cpp";
            default:
                throw new IllegalArgumentException("Unsupported language: " + language);
        }
    }
}
