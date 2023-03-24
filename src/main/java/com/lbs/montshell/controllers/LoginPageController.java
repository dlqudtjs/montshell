package com.lbs.montshell.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginPageController {

    @GetMapping("/loginPage")
    public String loginPage() {
        return "loginPage";
    }

//    @PostMapping("loginProc")
//    public String loginProc(Model model) {
//
//    }
}
