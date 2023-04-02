package com.lbs.montshell.services.submissionService;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;

@Configuration
public class DockerConfig {
    private final Path DOCKERFILE_PATH = Path.of("C:/Users/dlqud/OneDrive/바탕 화면/학교 공유 파일/3-1/Start-Up Project/montshell/temp/");
    private final Path TEMP_CODE_FILE_PATH = Path.of("C:/Users/dlqud/OneDrive/바탕 화면/학교 공유 파일/3-1/Start-Up Project/montshell/temp/");

    @Bean(name = "dockerfilePath")
    public Path dockerfilePath() {
        return DOCKERFILE_PATH;
    }

    @Bean(name = "tempCodeFilePath")
    public Path tempCodeFilePath() {
        return TEMP_CODE_FILE_PATH;
    }

    @Bean
    public DockerClientConfig dockerClientConfig() {
        return DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withDockerHost("tcp://127.0.0.1:2375") // Docker API 연결 정보 설정
                .build();
    }

    @Bean
    public DockerHttpClient httpClient(DockerClientConfig config) {
        return new ApacheDockerHttpClient.Builder()
                .dockerHost(config.getDockerHost())
                .sslConfig(config.getSSLConfig())
                .build();
    }

    @Bean
    public DockerClient dockerClient(DockerClientConfig config, DockerHttpClient httpClient) {
        return DockerClientImpl.getInstance(config, httpClient);
    }

    @Bean
    public CreateDockerfileService createDockerfileService() {
        return new CreateDockerfileServiceImpl(DOCKERFILE_PATH);
    }

    @Bean
    public CreateTempCodeFileService createTempCodeFileService() {
        return new CreateTempCodeFileServiceImpl(TEMP_CODE_FILE_PATH);
    }

    @Bean
    public BuildDockerfileService buildDockerfileService(DockerClient dockerClient) {
        return new BuildDockerfileServiceImpl(dockerClient);
    }

    @Bean
    public RunDockerContainerService runDockerContainerService(DockerClient dockerClient) {
        return new RunDockerContainerServiceImpl(dockerClient, DOCKERFILE_PATH);
    }
}
