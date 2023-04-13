package com.lbs.montshell.services.submissionService;

import com.lbs.montshell.controllers.SubmitForm;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Executor;

@Service
public class RunDockerService {
    CreateDockerfileService createDockerfileService;
    CreateTempCodeFileService createTempCodeFileService;
    BuildDockerfileService buildDockerfileService;
    RunDockerContainerService runDockerContainerService;
    TestCaseService testCaseService;

    private final Executor taskExecutor;

    public RunDockerService(
            CreateDockerfileService createDockerfileService,
            CreateTempCodeFileService createTempCodeFileService,
            BuildDockerfileService buildDockerfileService,
            RunDockerContainerService runDockerContainerService,
            TestCaseService testCaseService,
            @Qualifier("taskExecutor") Executor taskExecutor) {

        this.createDockerfileService = createDockerfileService;
        this.createTempCodeFileService = createTempCodeFileService;
        this.buildDockerfileService = buildDockerfileService;
        this.runDockerContainerService = runDockerContainerService;
        this.testCaseService = testCaseService;
        this.taskExecutor = taskExecutor;
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
