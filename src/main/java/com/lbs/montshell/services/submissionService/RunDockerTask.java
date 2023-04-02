package com.lbs.montshell.services.submissionService;

import com.lbs.montshell.controllers.SubmitForm;

import java.io.IOException;

public class RunDockerTask implements Runnable{
    private SubmitForm submitForm;
    private RunDockerService runDockerService;
    public RunDockerTask(SubmitForm submitForm, RunDockerService runDockerService) {
        this.submitForm = submitForm;
        this.runDockerService = runDockerService;
    }

    @Override
    public void run() {
        try {
            runDockerService.exeDocker(submitForm);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
