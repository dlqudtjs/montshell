package com.lbs.montshell.services.submissionService;

import com.lbs.montshell.controllers.SubmitForm;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class CreateDockerfileServiceImpl implements CreateDockerfileService {
    private final Path DOCKERFILE_PATH;

    public CreateDockerfileServiceImpl(@Qualifier("dockerfilePath") Path DOCKERFILE_PATH) {
        this.DOCKERFILE_PATH = DOCKERFILE_PATH;
    }

    @Override
    public Path createDockerfile(SubmitForm submitForm, String codeFileName) throws IOException {
        // 도커 파일 이름 생성 ex) java_1_1_dockerfile
        String userDockerfile = createDockerFileName(submitForm);

        // 도커 파일 내용 생성
        String dockerfileContent = getDockerfileContent(submitForm.getLanguage(), codeFileName);

        // dDOCKERFILE_PATH 에 userDockerfile 이름을 붙인다.
        Path dockerFile = DOCKERFILE_PATH.resolve(userDockerfile);

        // dockerFile 에 dockerfileContent 를 저장한다.
        Files.write(dockerFile, dockerfileContent.getBytes());

        return dockerFile;
    }

    public String createDockerFileName(SubmitForm submitForm) {
        return  submitForm.getLanguage().toLowerCase() + "_" + submitForm.getProblem_id() + "_" + submitForm.getUser_id() + "_" + "dockerfile";
    }

    public String getDockerfileContent(String language, String codeFileName) {
        switch (language.toLowerCase()) {
            case "java":
                return "FROM openjdk:11-jdk\n" +
                        "RUN mkdir /app\n" +
                        "WORKDIR /app\n" +
                        "COPY . /app\n" +
                        "RUN javac " + codeFileName + "\n" +
                        "CMD java " + codeFileName.replace(".java", " ") + "\n";
            case "cpp":
                return "FROM gcc:latest\n" +
                        "RUN mkdir /app\n" +
                        "WORKDIR /app\n" +
                        "COPY . /app\n" +
                        "RUN g++ -o " + codeFileName.replace(".cpp", " ") + " " + codeFileName + "\n" +
                        "CMD ./" + codeFileName.replace(".cpp", " ") + "\n";
            case "python":
                return "FROM python:3\n" +
                        "RUN mkdir /app\n" +
                        "WORKDIR /app\n" +
                        "COPY . /app\n" +
                        "CMD python "+  codeFileName + "\n";
            default:
                throw new IllegalArgumentException("Unsupported language: " + language);
        }
    }
}
