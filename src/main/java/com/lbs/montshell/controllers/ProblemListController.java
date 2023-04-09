package com.lbs.montshell.controllers;

import com.lbs.montshell.models.Problem;
import com.lbs.montshell.services.problemServices.ProblemService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProblemListController {

    private final ProblemService ProblemService;

    public ProblemListController(ProblemService problemService) {
        ProblemService = problemService;
    }

    @GetMapping("/problemList")
    public String problemList(Model model, @PageableDefault(sort = "id") Pageable pageable) {
        Page<Problem> problemList = ProblemService.problemList(pageable);
        model.addAttribute("problemList", problemList);
        model.addAttribute("totalPage", problemList.getTotalPages());
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());

        return "problemList";
    }
}
