package com.lbs.montshell.controllers;

import com.lbs.montshell.models.SubmitInfo;
import com.lbs.montshell.services.submitService.SubmitService;
import com.lbs.montshell.services.userServices.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;


@Controller
public class ResultListController {

    private final SubmitService submitService;

    public ResultListController(SubmitService submitService) {
        this.submitService = submitService;
    }


    @GetMapping("/user/resultList")
    public String problemList(Principal principal, Model model, @PageableDefault(sort = "id") Pageable pageable) {
        Page<SubmitInfo> submitInfoList = submitService.getSubmitInfoListByUserId(principal.getName(), pageable);
        int currentPage = submitInfoList.getNumber(); // 현재 페이지 번호
        int totalPages = submitInfoList.getTotalPages(); // 전체 페이지 수

        if(submitInfoList.isEmpty()) {
            model.addAttribute("message", "제출한 결과가 없습니다.");
        }

        model.addAttribute("submitInfoList", submitInfoList);
        model.addAttribute("totalPage", submitInfoList.getTotalPages());
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", (currentPage < totalPages - 1) ? currentPage + 1 : totalPages - 1);

        return "/user/resultList";
    }
}
