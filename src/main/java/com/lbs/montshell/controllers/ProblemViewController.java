package com.lbs.montshell.controllers;

import com.lbs.montshell.services.problemServices.ProblemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProblemViewController {

    private final ProblemService problemService;

    public ProblemViewController(ProblemService problemService) {
        this.problemService = problemService;
    }

    @GetMapping("/problemView")
    public String problemView(Model model, @RequestParam("problemId") Long problemId) {
        model.addAttribute("problem", problemService.getProblem(problemId).get());

        return "problemView";
    }
}
