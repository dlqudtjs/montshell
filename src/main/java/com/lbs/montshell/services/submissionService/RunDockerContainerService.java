package com.lbs.montshell.services.submissionService;

import com.lbs.montshell.controllers.SubmitForm;

import java.nio.file.Path;

public interface RunDockerContainerService {
    public void runDockerContainer(String dockerImageId, String dockerImageName, SubmitForm submitForm, String codeFileName);
}
