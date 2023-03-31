package com.lbs.montshell.services.submissionService;

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
    public Path createDockerfile(String language) throws IOException {
        String dockerfileContent = getDockerfileContent(language);
        Files.write(DOCKERFILE_PATH, dockerfileContent.getBytes());

        return DOCKERFILE_PATH;
    }

    public String getDockerfileContent(String language) {
        switch (language.toLowerCase()) {
            case "java":
                return "FROM openjdk:11-jdk\n" +
                        "RUN mkdir /app\n" +
                        "WORKDIR /app\n" +
                        "RUN javac Main.java\n" +
                        "CMD java Main\n";
            case "cpp":
                return "FROM gcc:latest\n" +
                        "RUN mkdir /app\n" +
                        "WORKDIR /app\n" +
                        "RUN g++ -o Main Main.cpp\n" +
                        "CMD ./Main\n";
            case "python":
                return "FROM python:3\n" +
                        "RUN mkdir /app\n" +
                        "WORKDIR /app\n" +
                        "CMD python Main.py\n";
            default:
                throw new IllegalArgumentException("Unsupported language: " + language);
        }
    }
}
