package com.lbs.montshell.services.submissionService;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.BuildImageCmd;
import com.github.dockerjava.api.command.BuildImageResultCallback;
import com.github.dockerjava.api.model.BuildResponseItem;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class BuildDockerfileServiceImpl implements BuildDockerfileService {
    private final DockerClient dockerClient;

    public BuildDockerfileServiceImpl(DockerClient dockerClient) {
        this.dockerClient = dockerClient;
    }

    @Override
    public String buildDockerfile(Path dockerfilePath, String imageName) {
        try {
            System.out.println("dockerfilePath: " + dockerfilePath);
            // build docker image
            BuildImageCmd buildImageCmd = dockerClient.buildImageCmd()
                    // 도커 파일 경로를 설정
                    .withDockerfile(dockerfilePath.toFile())
                    // 이미지 이름을 설정
                    .withTags(Set.of(imageName + ":latest"))
                    // 캐시를 사용하지 않도록 설정
                    .withNoCache(true)
                    // 도커 파일이 있는 디렉터리를 설정
                    .withPull(true);

            // 이미지 빌드 결과를 저장할 AtomicReference 생성
            AtomicReference<String> imageId = new AtomicReference<>();

            // 콜백 클래스를 생성하여 이미지 빌드 결과 처리
            BuildImageResultCallback callback = new BuildImageResultCallback() {
                @Override
                // onNext 메소드를 오버라이드하여 이미지 빌드 결과를 처리
                public void onNext(BuildResponseItem item) {
                    // 이미지 빌드가 완료되면 이미지 ID를 저장
                    if (item.getImageId() != null) {
                        imageId.set(item.getImageId());
                    }
                    // 이미지 빌드 중 오류가 발생하면 오류 메시지를 출력
                    if (item.getErrorDetail() != null) {
                        System.err.println(item.getErrorDetail().getMessage());
                    }
                    // 이미지 빌드 중 출력되는 메시지를 출력
                    System.out.println(item.getStream());
                    // 부모 클래스의 onNext 메소드를 호출하여 이미지 빌드 결과를 처리
                    super.onNext(item);
                }
            };

            buildImageCmd.exec(callback).awaitCompletion();
            System.out.println("도커 이미지 빌드가 완료되었습니다. 이미지 ID: " + imageId);

            return imageId.get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
