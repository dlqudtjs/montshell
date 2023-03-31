package com.lbs.montshell.services.submissionService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RunDockerServiceTest {
    private final Path DOCKERFILE_PATH = Path.of("C:\\Users\\dlqud\\OneDrive\\바탕 화면\\학교 공유 파일\\3-1\\Start-Up Project\\montshell\\Dockerfile");
    private final Path TEMP_CODE_FILE_PATH = Path.of("C:\\Users\\dlqud\\OneDrive\\바탕 화면\\학교 공유 파일\\3-1\\Start-Up Project\\montshell\\");

    CreateTempCodeFileService createTempCodeFileService = new CreateTempCodeFileServiceImpl(TEMP_CODE_FILE_PATH);
    CreateDockerfileService createDockerfileService = new CreateDockerfileServiceImpl(DOCKERFILE_PATH);

    @Test
    @DisplayName("Dockerfile 생성 및 내용 테스트")
    void createDockerfile() throws IOException {
        String language_java = "java";
        String fileContent_java =
                "FROM openjdk:11-jdk\n" +
                "RUN mkdir /app\n" +
                "WORKDIR /app\n" +
                "RUN javac Main.java\n" +
                "CMD java Main\n";

        createDockerfileService.createDockerfile(language_java);
        assertTrue(Files.exists(DOCKERFILE_PATH));

        String fileContent = Files.readString(DOCKERFILE_PATH);
        assertEquals(fileContent_java, fileContent);
    }

    @Test
    @DisplayName("임시 코드 파일 생성 테스트")
    void createTempCodeFile() throws IOException {
        String code = "print('test');";
        String language = "java";

        Path tempCodeFilePath = createTempCodeFileService.createTempCodeFile(code, language);
        assertTrue(Files.exists(tempCodeFilePath));

        String fileContent = Files.readString(tempCodeFilePath);
        assertEquals(code, fileContent);
    }
}