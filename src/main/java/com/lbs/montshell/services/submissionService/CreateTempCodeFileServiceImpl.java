package com.lbs.montshell.services.submissionService;

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
    public Path createTempCodeFile(String code, String language) throws IOException {
        String codeFileName = getCodeFileName(language);
        Path codeFilePath = TEMP_CODE_FILE_PATH.resolve(codeFileName);
        Files.write(codeFilePath, code.getBytes());

        return codeFilePath;
    }

    public String getCodeFileName(String language) {

        switch (language.toLowerCase()) {
            case "java":
                return "Main.java";
            case "python":
                return "main.py";
            case "cpp":
                return "main.cpp";
            default:
                throw new IllegalArgumentException("Unsupported language: " + language);
        }
    }
}
