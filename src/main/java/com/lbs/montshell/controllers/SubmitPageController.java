package com.lbs.montshell.controllers;

import com.lbs.montshell.services.problemServices.ProblemService;
import com.lbs.montshell.services.submissionService.RunDockerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SubmitPageController {

    RunDockerService runDockerService;
    ProblemService problemService;

    public SubmitPageController(RunDockerService runDockerService, ProblemService problemService) {
        this.runDockerService = runDockerService;
        this.problemService = problemService;
    }
    @GetMapping("/user/submit")
    public String submit(Model model, @RequestParam("problemId") Long problemId) {
        model.addAttribute("problem", problemService.getProblem(problemId).get());

        return "/user/submit";
    }

    @PostMapping("/submitProc")
    public String submitProc(SubmitForm submitForm) {
        runDockerService.runDockerInParallel(submitForm);

        return "redirect:/";
    }
}
