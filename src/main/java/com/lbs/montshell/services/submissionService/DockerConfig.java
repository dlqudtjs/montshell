//package com.lbs.montshell.services.submissionService;
//
//import com.github.dockerjava.api.DockerClient;
//import com.github.dockerjava.core.DefaultDockerClientConfig;
//import com.github.dockerjava.core.DockerClientBuilder;
//import com.github.dockerjava.core.DockerClientConfig;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.DependsOn;
//
//@Configuration
//public class DockerConfig {
//
//    @Bean
//    public DockerClientConfig dockerClientConfig() {
//        return DefaultDockerClientConfig.createDefaultConfigBuilder()
//                .withDockerHost("tcp://localhost:80") // Docker API 연결 정보 설정
//                .build();
//    }
//
//    @Bean
//    public DockerClient dockerClient(DockerClientConfig config) {
//        return DockerClientBuilder.getInstance(config, httpClient);
//    }
//}