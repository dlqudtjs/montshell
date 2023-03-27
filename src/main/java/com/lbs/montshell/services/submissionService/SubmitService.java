package com.lbs.montshell.services.submissionService;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.BuildImageResultCallback;
import com.github.dockerjava.api.model.BuildResponseItem;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;
import com.lbs.montshell.controllers.SubmitForm;
import org.springframework.stereotype.Service;

import java.io.File;


@Service
public class SubmitService {

    DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder()
            .withDockerHost("tcp://localhost:80") // Docker API 연결 정보 설정
            .build();
    DockerHttpClient httpClient = new ApacheDockerHttpClient.Builder()
            .dockerHost(config.getDockerHost())
            .sslConfig(config.getSSLConfig())
            .build();

    DockerClient dockerClient = DockerClientImpl.getInstance(config, httpClient);

    public void buildDockerImage(SubmitForm submitForm) {
        String dockerfileDir = "C:\\Users\\dlqud\\OneDrive\\바탕 화면\\학교 공유 파일\\3-1\\Start-Up Project\\montshell"; // Dockerfile이 있는 디렉토리 경로
        String imageName = submitForm.getProblem_id() + "-" + submitForm.getUser_id() + "-" + submitForm.getLanguage().toLowerCase(); // 도커 이미지 이름

        try {
            // 도커 이미지 빌드
            BuildImageResultCallback callback = new BuildImageResultCallback() {
                @Override
                public void onNext(BuildResponseItem item) {
                    System.out.println(item.getStream());
                    super.onNext(item);
                }
            };
            dockerClient.buildImageCmd(new File(dockerfileDir))
                    .withBuildArg("LANGUAGE", submitForm.getLanguage())
                    .withBuildArg("CODE", submitForm.getCode())
                    .withTag(imageName)
                    .exec(callback)
                    .awaitCompletion();

            System.out.println("도커 이미지 빌드가 완료되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("도커 이미지 빌드에 실패하였습니다.");
        }
    }
}

