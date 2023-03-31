package com.lbs.montshell.services.submissionService;

import com.lbs.montshell.controllers.SubmitForm;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;

@Service
public class RunDockerService {
    CreateDockerfileService createDockerfileService;
    CreateTempCodeFileService createTempCodeFileService;
    BuildDockerfileService buildDockerfileService;
    RunDockerContainerService runDockerContainerService;

    public RunDockerService(
            CreateDockerfileService createDockerfileService,
            CreateTempCodeFileService createTempCodeFileService,
            BuildDockerfileService buildDockerfileService,
            RunDockerContainerService runDockerContainerService) {

        this.createDockerfileService = createDockerfileService;
        this.createTempCodeFileService = createTempCodeFileService;
        this.buildDockerfileService = buildDockerfileService;
        this.runDockerContainerService = runDockerContainerService;
    }

    public void exeDocker(SubmitForm submitForm) throws IOException {
        String dockerImageName = getDockerImageName(submitForm);

        // create dockerfile
        Path dockerfilePath = createDockerfileService.createDockerfile(submitForm.getLanguage());

        // create temp code file
        Path codefilePath = createTempCodeFileService.createTempCodeFile(submitForm.getCode(), submitForm.getLanguage());

        // build docker image
        String imageId = buildDockerfileService.buildDockerfile(dockerfilePath, dockerImageName);

        // run docker image
        runDockerContainerService.runDockerContainer(imageId, dockerImageName, submitForm, dockerfilePath);
    }

    public String getDockerImageName(SubmitForm submitForm) {
        return submitForm.getProblem_id() + "_" + submitForm.getUser_id() + "_" + submitForm.getLanguage().toLowerCase();
    }
}
