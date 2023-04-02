package com.lbs.montshell.services.submissionService;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.Bind;
import com.lbs.montshell.controllers.SubmitForm;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.nio.file.Path;

@Service
public class RunDockerContainerServiceImpl implements RunDockerContainerService {
    private DockerClient dockerClient;
    private final Path DOCKERFILE_PATH;

    public RunDockerContainerServiceImpl(DockerClient dockerClient, @Qualifier("dockerfilePath") Path DOCKERFILE_PATH) {
        this.dockerClient = dockerClient;
        this.DOCKERFILE_PATH = DOCKERFILE_PATH;
    }

    @Override
    public void runDockerContainer(String dockerImageId, String dockerImageName, SubmitForm submitForm, String codeFileName) {
        // cmd 명령어를 가져올 때 필요
        String language = submitForm.getLanguage().toLowerCase();
        
        // 현재 디렉토리와 바인딩할 디렉토리를 매치 시켜줘야함
        String bindString =  DOCKERFILE_PATH.toString() + "/" + codeFileName + ":/app/" + codeFileName;

        try {
            // 도커 컨테이너 실행
            CreateContainerResponse container = dockerClient.createContainerCmd(dockerImageId)
                    // 컨테이너 이름 설정
                    .withName(dockerImageName)
                    // 컨테이너 커맨드 설정
                    .withCmd(getDockerCommand(language, codeFileName))
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

    public String[] getDockerCommand(String language, String dockerFile) {
        switch (language.toLowerCase()) {
            case "java":
                return new String[]{"java", dockerFile.replace(".java", "")};
            case "cpp":
                return new String[]{"./" + dockerFile.replace(".cpp", "")};
            case "python":
                return new String[]{"python", dockerFile};
            default:
                throw new IllegalArgumentException("Unsupported language: " + language);
        }
    }
}
