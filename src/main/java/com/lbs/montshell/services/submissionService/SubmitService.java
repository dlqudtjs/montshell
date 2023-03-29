package com.lbs.montshell.services.submissionService;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.BuildImageCmd;
import com.github.dockerjava.api.command.BuildImageResultCallback;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.BuildResponseItem;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.transport.DockerHttpClient;
import com.lbs.montshell.controllers.SubmitForm;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;


@Service
public class SubmitService {

    DockerClientConfig config;
    DockerHttpClient httpClient;
    DockerClient dockerClient;

    public SubmitService(DockerClientConfig config, DockerHttpClient httpClient, DockerClient dockerClient) {
        this.config = config;
        this.httpClient = httpClient;
        this.dockerClient = dockerClient;
    }
    public void buildAndRunDockerContainer(SubmitForm submitForm) {
        String imageName = submitForm.getProblem_id() + "-" + submitForm.getUser_id() + "-" + submitForm.getLanguage().toLowerCase(); // 도커 이미지 이름

        // 도커 이미지 빌드
        String imageId = buildDockerImage(submitForm);

        if (imageId != null) {
            // 도커 컨테이너 실행
            runDockerContainer(imageId, imageName);
        }
    }

    public String buildDockerImage(SubmitForm submitForm) {
        Path dockerfileDir = Paths.get( "C:/Users/dlqud/OneDrive/바탕 화면/학교 공유 파일/3-1/Start-Up Project/montshell"); // Dockerfile이 있는 디렉토리 경로
        String imageName = submitForm.getProblem_id() + "-" + submitForm.getUser_id() + "-" + submitForm.getLanguage().toLowerCase(); // 도커 이미지 이름

        try {
            // 도커 이미지 빌드
            BuildImageCmd buildImageCmd = dockerClient.buildImageCmd()
                    .withDockerfile(new File(dockerfileDir.toFile(), "Dockerfile"))
                    .withBuildArg("LANGUAGE", submitForm.getLanguage())
                    .withBuildArg("CODE", submitForm.getCode())
                    .withTags(Set.of(imageName));

            AtomicReference<String> imageId = new AtomicReference<>();

            BuildImageResultCallback callback = new BuildImageResultCallback() {
                @Override
                public void onNext(BuildResponseItem item) {
                    if (item.getImageId() != null) {
                        imageId.set(item.getImageId());
                    }
                    System.out.println(item.getStream());
                    super.onNext(item);
                }
            };

            buildImageCmd.exec(callback).awaitCompletion();
            System.out.println("도커 이미지 빌드가 완료되었습니다. 이미지 ID: " + imageId);
            return imageId.get();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("도커 이미지 빌드에 실패하였습니다.");
            return null;
        }
    }

    public void runDockerContainer(String imageId, String imageName) {
        try {
            CreateContainerResponse container = dockerClient.createContainerCmd(imageId)
                    .withName(imageName)
                    .exec();

            dockerClient.startContainerCmd(container.getId()).exec();

            System.out.println("Docker container started. Container ID: " + container.getId());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Docker container 실행에 실패하였습니다.");
        }
    }
}

