package com.lbs.montshell.controllers;

import com.lbs.montshell.services.submissionService.RunDockerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;

@Controller
public class SubmitPageController {

    RunDockerService runDockerService;

    public SubmitPageController(RunDockerService runDockerService) {
        this.runDockerService = runDockerService;
    }

    @GetMapping("/submitPage")
    public String submitPage() {

        return "user/submitPage";
    }

    @PostMapping("/submitProc")
    public String submitProc(SubmitForm submitForm) throws IOException {
        runDockerService.exeDocker(submitForm);

        return "redirect:/";
    }
}
