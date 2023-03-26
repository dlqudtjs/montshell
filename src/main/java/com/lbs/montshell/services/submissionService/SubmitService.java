package com.lbs.montshell.services.submissionService;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.BuildImageResultCallback;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.exception.DockerException;
import com.github.dockerjava.api.model.HostConfig;
import com.lbs.montshell.controllers.SubmitForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class SubmitService {

    private DockerClient dockerClient;

    public SubmitService(DockerClient dockerClient) {
        this.dockerClient = dockerClient;
    }

    public void submitCode(SubmitForm submitForm) throws DockerException, InterruptedException {

        // Dockerfile에 전송된 사용자 코드를 추가하여 이미지를 빌드
        String dockerfilePath = "baseDockerfile_" + submitForm.getLanguage();
        String imageTag = submitForm.getProblem_id() + "_" + submitForm.getUser_id() + "_" + submitForm.getLanguage();

        Path dockerfile = Paths.get(dockerfilePath);
        BuildImageResultCallback resultCallback = new BuildImageResultCallback();

        dockerClient.buildImageCmd(dockerfile.toFile())
                .withBuildArg("code", submitForm.getCode())
                .withTag(imageTag)
                .exec(resultCallback).awaitImageId();

        String imageId = resultCallback.awaitImageId();

        // Docker 컨테이너 생성 및 실행
        HostConfig hostConfig = HostConfig.newHostConfig().withNetworkMode("host");

        CreateContainerResponse containerResponse = dockerClient.createContainerCmd(imageTag)
                .withHostConfig(hostConfig)
                .withName(imageTag)
                .exec();

        dockerClient.startContainerCmd(containerResponse.getId()).exec();
    }
}

