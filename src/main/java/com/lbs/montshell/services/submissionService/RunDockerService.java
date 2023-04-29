package com.lbs.montshell.services.submissionService;

import com.lbs.montshell.controllers.SubmitForm;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Service
public class RunDockerService {
    private final Path RESULT_FILE_PATH;
    CreateDockerfileService createDockerfileService;
    CreateTempCodeFileService createTempCodeFileService;
    BuildDockerfileService buildDockerfileService;
    RunDockerContainerService runDockerContainerService;
    TestCaseService testCaseService;
    SaveResultService saveResultService;

    private final Executor taskExecutor;

    public RunDockerService(
            CreateDockerfileService createDockerfileService,
            CreateTempCodeFileService createTempCodeFileService,
            BuildDockerfileService buildDockerfileService,
            RunDockerContainerService runDockerContainerService,
            TestCaseService testCaseService,
            SaveResultService saveResultService,
            @Qualifier("taskExecutor") Executor taskExecutor,
            @Qualifier("resultFilePath") Path RESULT_FILE_PATH) {

        this.createDockerfileService = createDockerfileService;
        this.createTempCodeFileService = createTempCodeFileService;
        this.buildDockerfileService = buildDockerfileService;
        this.runDockerContainerService = runDockerContainerService;
        this.testCaseService = testCaseService;
        this.taskExecutor = taskExecutor;
        this.saveResultService = saveResultService;
        this.RESULT_FILE_PATH = RESULT_FILE_PATH;
    }

    public void exeDocker(SubmitForm submitForm) throws IOException {
        // replace language
        replaceLanguage(submitForm);

        // get docker image name
        String dockerImageName = getDockerImageName(submitForm);

        // create temp code file
        String codeFileName = createTempCodeFileService.createTempCodeFile(submitForm);

        // create input file
        String inputFileName = testCaseService.createInputFile(submitForm.getProblem_id());

        // create output file
        String outputFileName = testCaseService.createOutputFile(submitForm.getProblem_id());

        // create dockerfile
        Path dockerfilePath = createDockerfileService.createDockerfile(submitForm, codeFileName, inputFileName, outputFileName);

        // build docker image
        String imageId = buildDockerfileService.buildDockerfile(dockerfilePath, dockerImageName);

        // run docker image
        runDockerContainerService.runDockerContainer(imageId, dockerImageName, submitForm, codeFileName);

        //결과 파일 대기
        CompletableFuture<File> resultFileFuture = waitForResultFileAsync(RESULT_FILE_PATH, submitForm);

        // save result
        resultFileFuture.thenAcceptAsync(resultFile -> {
            try {
                saveResultService.saveResult(submitForm, Path.of(RESULT_FILE_PATH + "\\" + getResultFileName(submitForm)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public CompletableFuture<File> waitForResultFileAsync(Path resultFilePath, SubmitForm submitForm) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return waitForResultFile(resultFilePath, 60, submitForm);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                return null;
            }
        });
    }

    public File waitForResultFile(Path resultFilePath, int maxWaitSeconds, SubmitForm submitForm) throws IOException, InterruptedException {
        String resultFileName = resultFilePath + "\\" + getResultFileName(submitForm).toString();
        System.out.println("resultFileName = " + resultFileName);
        File resultFile = new File(resultFileName);
        int waitTime = 0;

        // 파일이 존재할 때까지 대기하거나 최대 대기 시간이 초과될 때까지 대기
        while (!resultFile.exists() && waitTime < maxWaitSeconds * 1000) {
            Thread.sleep(1000); // 1초 간격으로 확인
            waitTime += 1000;
        }

        if (waitTime >= maxWaitSeconds * 1000) {
            System.out.println("Max wait time exceeded");
            return null; // 최대 대기 시간이 초과된 경우 null 반환
        }

        System.out.println("Result file found");
        return resultFile;
    }
    public Path getResultFileName(SubmitForm submitForm) {
        return Path.of(submitForm.getUser_id() + "_"  + submitForm.getProblem_id()  + "_result.txt");
    }

    public void runDockerInParallel(SubmitForm submitForm) {
        RunDockerTask task = new RunDockerTask(submitForm, this);
        taskExecutor.execute(task);
    }

    public String getDockerImageName(SubmitForm submitForm) {
        return submitForm.getProblem_id() + "_" + submitForm.getUser_id() + "_" + submitForm.getLanguage().toLowerCase();
    }

    public void replaceLanguage(SubmitForm submitForm) {
        if (submitForm.getLanguage().equals("c++")) {
            submitForm.setLanguage("cpp");
        }
    }
}
