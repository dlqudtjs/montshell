//package com.lbs.montshell.services.submissionService;
//
//import com.github.dockerjava.api.DockerClient;
//import com.github.dockerjava.api.command.BuildImageCmd;
//import com.github.dockerjava.api.command.BuildImageResultCallback;
//import com.github.dockerjava.api.command.CreateContainerResponse;
//import com.github.dockerjava.api.model.Bind;
//import com.github.dockerjava.api.model.BuildResponseItem;
//import com.lbs.montshell.controllers.SubmitForm;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.Set;
//import java.util.concurrent.atomic.AtomicReference;
//
//
//@Service
//public class SubmitService {
//    DockerClient dockerClient;
//
//    public SubmitService(DockerClient dockerClient) {
//        this.dockerClient = dockerClient;
//    }
//
//    public void buildAndRunDockerContainer(SubmitForm submitForm) throws IOException {
//        if (submitForm.getLanguage().equals("c++")) {
//            submitForm.setLanguage("cpp");
//        }
//
//        String imageName = submitForm.getProblem_id() + "-" + submitForm.getUser_id() + "-" + submitForm.getLanguage().toLowerCase(); // 도커 이미지 이름
//
//        // Create temporary file for user code
//        Path tempCodePath = createTempUserCodeFile(submitForm);
//
//        // 도커 이미지 빌드
//        String imageId = buildDockerImage(submitForm, imageName);
//
//        if (imageId != null) {
//            // 도커 컨테이너 실행
//            runDockerContainer(imageId, imageName, submitForm, tempCodePath);
//        }
//    }
//    private String getTempFileName(String language) {
//        switch (language) {
//            case "java":
//                return "Main.java";
//            case "cpp":
//                return "Main.cpp";
//            case "python":
//                return "Main.py";
//            default:
//                throw new IllegalArgumentException("Unsupported language: " + language);
//        }
//    }
//
//    private Path createTempUserCodeFile(SubmitForm submitForm) throws IOException {
//        String language = submitForm.getLanguage().toLowerCase();
//        String codeFilename = getTempFileName(language);
//        Path tempCodePath = Files.createTempFile("user_code_", codeFilename);
//        Files.write(tempCodePath, submitForm.getCode().getBytes());
//        return tempCodePath;
//    }
//
//    public String buildDockerImage(SubmitForm submitForm, String imageName) {
//        String language = submitForm.getLanguage();
//        String dockerfileContent;
//        Path dockerfilePath = Paths.get("C:\\Users\\dlqud\\OneDrive\\바탕 화면\\학교 공유 파일\\3-1\\Start-Up Project\\montshell\\Dockerfile");
//
//        // 언어별 Dockerfile 내용 설정
//        switch (language.toLowerCase()) {
//            case "java":
//                dockerfileContent = "FROM openjdk:11-jdk\n" +
//                        "RUN mkdir /app\n" +
//                        "WORKDIR /app\n" +
//                        "COPY . /app\n" +
//                        "RUN javac Main.java\n" +
//                        "CMD java Main\n";
//                break;
//            case "cpp":
//                dockerfileContent = "FROM gcc:latest\n" +
//                        "RUN mkdir /app\n" +
//                        "WORKDIR /app\n" +
//                        "COPY . /app\n" +
//                        "RUN g++ -o Main Main.cpp\n" +
//                        "CMD ./Main\n";
//                break;
//            case "python":
//                dockerfileContent = "FROM python:3\n" +
//                        "RUN mkdir /app\n" +
//                        "WORKDIR /app\n" +
//                        "COPY . /app\n" +
//                        "RUN ls -a\n" +
//                        "CMD python Main.py\n";
//                break;
//            default:
//                System.out.println("지원하지 않는 언어입니다.");
//                return null;
//        }
//
//        try {
//            Path javaFile = Paths.get("C:\\Users\\dlqud\\OneDrive\\바탕 화면\\학교 공유 파일\\3-1\\Start-Up Project\\montshell\\Main.cpp");
//
//            // Dockerfile 생성
//            Files.write(dockerfilePath, dockerfileContent.getBytes());
//            Files.write(javaFile, submitForm.getCode().getBytes());
//
//            // Build image
//            BuildImageCmd buildImageCmd = dockerClient.buildImageCmd()
//                    .withDockerfile(dockerfilePath.toFile())
//                    .withTags(Set.of(imageName + ":latest"))
//                    .withNoCache(true)
//                    .withPull(true);
//
//            AtomicReference<String> imageId = new AtomicReference<>();
//
//            BuildImageResultCallback callback = new BuildImageResultCallback() {
//                @Override
//                public void onNext(BuildResponseItem item) {
//                    if (item.getImageId() != null) {
//                        imageId.set(item.getImageId());
//                    }
//                    if (item.getErrorDetail() != null) {
//                        System.err.println(item.getErrorDetail().getMessage());
//                    }
//                    System.out.println(item.getStream());
//                    super.onNext(item);
//                }
//            };
//
//            buildImageCmd.exec(callback).awaitCompletion();
//            System.out.println("도커 이미지 빌드가 완료되었습니다. 이미지 ID: " + imageId);
//
//            return imageId.get();
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("도커 이미지 빌드에 실패하였습니다.");
//            return null;
//        }
//    }
//
//    public void runDockerContainer(String imageId, String imageName, SubmitForm submitForm, Path tempCodePath) {
//        try {
//            String language = submitForm.getLanguage().toLowerCase();
//            String codeFilename = getTempFileName(language);
//
//            String bindString = tempCodePath.toString() + ":/app/" + codeFilename;
//
//            CreateContainerResponse container = dockerClient.createContainerCmd(imageId)
//                    .withName(imageName)
//                    .withCmd(getDockerCommand(submitForm))
//                    .withBinds(Bind.parse(bindString))
//                    .exec();
//
//            dockerClient.startContainerCmd(container.getId()).exec();
//
//            System.out.println("Docker container started. Container ID: " + container.getId());
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("Docker container 실행에 실패하였습니다.");
//        }
//    }
//
//    private String[] getDockerCommand(SubmitForm submitForm) {
//        String language = submitForm.getLanguage().toLowerCase();
//
//        switch (language) {
//            case "java":
//                return new String[]{"java", "Main"};
//            case "cpp":
//                return new String[]{"./Main"};
//            case "python":
//                return new String[]{"python", "Main.py"};
//            default:
//                throw new IllegalArgumentException("Unsupported language: " + language);
//        }
//    }
//
//    private String getExtension(String language) {
//        switch (language) {
//            case "java":
//                return "java";
//            case "cpp":
//                return "cpp";
//            case "python":
//                return "py";
//            default:
//                throw new IllegalArgumentException("Unsupported language: " + language);
//        }
//    }
//}
