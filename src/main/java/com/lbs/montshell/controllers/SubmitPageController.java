package com.lbs.montshell.controllers;

import com.lbs.montshell.services.submissionService.SubmitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SubmitPageController {

    SubmitService submitService;
    public SubmitPageController(SubmitService submitService) {
        this.submitService = submitService;
    }

    @GetMapping("/submitPage")
    public String submitPage() {

        return "user/submitPage";
    }

    @PostMapping("/submitProc")
    public String submitProc(SubmitForm submitForm) throws InterruptedException {
        submitService.buildAndRunDockerContainer(submitForm);

        return "redirect:/";
    }
}
