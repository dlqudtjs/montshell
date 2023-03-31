package com.lbs.montshell.services.submissionService;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.Bind;
import com.lbs.montshell.controllers.SubmitForm;
import org.springframework.stereotype.Service;

import java.nio.file.Path;

@Service
public class RunDockerContainerServiceImpl implements RunDockerContainerService {
    private DockerClient dockerClient;

    public RunDockerContainerServiceImpl(DockerClient dockerClient) {
        this.dockerClient = dockerClient;
    }

    @Override
    public void runDockerContainer(String dockerImageId, String dockerImageName, SubmitForm submitForm, Path tempCodePath) {
        String language = submitForm.getLanguage().toLowerCase();
        String codeFilename = getTempFileName(language);
        String bindString = tempCodePath.toString() + ":/app" + codeFilename;

        try {
            // 도커 컨테이너 실행
            CreateContainerResponse container = dockerClient.createContainerCmd(dockerImageId)
                    // 컨테이너 이름 설정
                    .withName(dockerImageName)
                    // 컨테이너 커맨드 설정
                    .withCmd(getDockerCommand(language))
                    // 컨테이너 내부에 바인드할 디렉토리 설정
                    .withBinds(Bind.parse(bindString))
                    // 컨테이너 생성
                    .exec();

            // 컨테이너 시작
            dockerClient.startContainerCmd(container.getId()).exec();

            System.out.println("Docker container started. Container ID: " + container.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getTempFileName(String language) {
        switch (language.toLowerCase()) {
            case "java":
                return "Main.java";
            case "cpp":
                return "Main.cpp";
            case "python":
                return "Main.py";
            default:
                throw new IllegalArgumentException("Unsupported language: " + language);
        }
    }

    public String[] getDockerCommand(String language) {
        switch (language.toLowerCase()) {
            case "java":
                return new String[]{"java", "Main"};
            case "cpp":
                return new String[]{"./Main"};
            case "python":
                return new String[]{"python", "Main.py"};
            default:
                throw new IllegalArgumentException("Unsupported language: " + language);
        }
    }
}
