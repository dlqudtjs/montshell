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
        String shebang = getShebang(submitForm.getLanguage());

        // java 의 경우 class Main 의 이름을 codeFileName 으로 바꾸어야 한다.
        submitForm.setCode(modifyMainClassName(submitForm.getLanguage(), codeFileName, submitForm.getCode()));

        // shebang 와 수정한 코드를 이어 붙이고 file 을 생성한다.
        Files.write(codeFilePath, (shebang + submitForm.getCode()).getBytes());

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

    public String getShebang(String language) {
        switch (language.toLowerCase()) {
            case "python":
                return "#!/usr/bin/env python3";
            default:
                return "";
        }
    }

    public String modifyMainClassName(String language, String codeFileName, String code) {
        // 일단 java 만 처리한다.
        switch (language.toLowerCase()) {
            case "java":
                return code.replace("class Main", "class " + codeFileName.replace(".java", ""));
            default:
                return code;

        }
    }
}
