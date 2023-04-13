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
    public Path createDockerfile(SubmitForm submitForm, String codeFileName, String inputFileName, String outputFileName) throws IOException {
        // 스크립트 파일 이름 생성 ex) java_1_1.sh
        String scriptFileName = createScriptFileName(submitForm);

        // 스크립트 파일 내용 생성
        String scriptFileContent = getScriptFileContent(submitForm, codeFileName, inputFileName, outputFileName);

        // Dockerfile 을 생성할 경로에 scriptFileName 을 붙인다. (경로가 같다)
        Path scriptFile = DOCKERFILE_PATH.resolve(scriptFileName);

        // 스크립트 파일을 생성한다.
        Files.write(scriptFile, scriptFileContent.getBytes());

        // 도커 파일 이름 생성 ex) java_1_1_dockerfile
        String userDockerfile = createDockerFileName(submitForm);

        // 도커 파일 내용 생성
        String dockerfileContent = getDockerfileContent(submitForm.getLanguage(), codeFileName, scriptFileName);

        // dDOCKERFILE_PATH 에 userDockerfile 이름을 붙인다.
        Path dockerFile = DOCKERFILE_PATH.resolve(userDockerfile);

        // dockerFile 에 dockerfileContent 를 저장한다.
        Files.write(dockerFile, dockerfileContent.getBytes());

        return dockerFile;
    }

    public String createDockerFileName(SubmitForm submitForm) {
        return  submitForm.getLanguage().toLowerCase() + "_" + submitForm.getProblem_id() + "_" + submitForm.getUser_id() + "_" + "dockerfile";
    }

    public String getDockerfileContent(String language, String codeFileName, String scriptFileName) {
        switch (language.toLowerCase()) {
            case "java":
                return "FROM openjdk:11-jdk\n" +
                        "RUN mkdir /app\n" +
                        "WORKDIR /app\n" +
                        "COPY . /app\n" +
                        "RUN javac " + codeFileName + "\n" +
                        "COPY " + scriptFileName + " /app\n" +
                        "RUN chmod +x " + scriptFileName + "\n" +
                        "ENTRYPOINT [\"/app/" + scriptFileName + "\"]\n";
            case "cpp":
                return "FROM gcc:latest\n" +
                        "RUN mkdir /app\n" +
                        "WORKDIR /app\n" +
                        "COPY . /app\n" +
                        "RUN g++ -o " + codeFileName.replace(".cpp", " ") + " " + codeFileName + "\n" +
                        "COPY " + scriptFileName + " /app\n" +
                        "RUN chmod +x " + scriptFileName + "\n" +
                        "ENTRYPOINT [\"/app/" + scriptFileName + "\"]\n";
            case "python":
                return "FROM python:3\n" +
                        "RUN mkdir /app\n" +
                        "WORKDIR /app\n" +
                        "COPY . /app\n" +
                        "COPY " + scriptFileName + " /app\n" +
                        "RUN chmod +x " + scriptFileName + "\n" +
                        "ENTRYPOINT [\"/app/" + scriptFileName + "\"]\n";
            default:
                throw new IllegalArgumentException("Unsupported language: " + language);
        }
    }

    public String getScriptFileContent(SubmitForm submitForm, String codeFileName, String inputFileName, String outputFileName) {
        String user_output = submitForm.getUser_id() + "_output.txt";

        switch (submitForm.getLanguage().toLowerCase()) {
            case "java":
                return "#!/bin/bash\n" +
                        "java " + codeFileName.replace(".java", " ") + " < " + inputFileName + " > " + user_output + " 2>&1\n" +
                        "echo \"----user output----\" \n" +
                        "cat " + user_output + "\n" +
                        "echo \"----correct output----\" \n" +
                        "cat " + outputFileName + "\n" +
                        "diff -q -w " + user_output + " " + outputFileName + " > /dev/null\n" +
                        "if [ $? -eq 0 ]; then\n" +
                        "    echo \"\nCorrect\"\n" +
                        "else\n" +
                        "    echo \"\nWrong\"\n" +
                        "fi\n";
            case "cpp":
                return "#!/bin/bash\n" +
                        "./" + codeFileName.replace(".cpp", " ") + " < " + inputFileName + " > " + user_output + " 2>&1\n" +
                        "echo \"----user output----\" \n" +
                        "cat " + user_output + "\n" +
                        "echo \"----correct output----\" \n" +
                        "cat " + outputFileName + "\n" +
                        "diff -q -w " + user_output + " " + outputFileName + " > /dev/null\n" +
                        "if [ $? -eq 0 ]; then\n" +
                        "    echo \"\nCorrect\"\n" +
                        "else\n" +
                        "    echo \"\nWrong\"\n" +
                        "fi\n";
            case "python":
                return "#!/bin/bash\n" +
                        "python3 " + codeFileName + " < " + inputFileName + " > " + user_output + " 2>&1\n" +
                        "echo \"----user output----\" \n" +
                        "cat " + user_output + "\n" +
                        "echo \"----correct output----\" \n" +
                        "cat " + outputFileName + "\n" +
                        "diff -q -w " + user_output + " " + outputFileName + " > /dev/null\n" +
                        "if [ $? -eq 0 ]; then\n" +
                        "    echo \"\nCorrect\"\n" +
                        "else\n" +
                        "    echo \"\nWrong\"\n" +
                        "fi\n";
            default:
                throw new IllegalArgumentException("Unsupported language: " + submitForm.getLanguage());
        }
    }

    public String createScriptFileName(SubmitForm submitForm) {
        return  submitForm.getLanguage().toLowerCase() + "_" + submitForm.getProblem_id() + "_" + submitForm.getUser_id() + "_" + "script.sh";
    }
}
