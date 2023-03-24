package com.lbs.montshell.controllers;

import com.lbs.montshell.models.User;
import com.lbs.montshell.services.userServices.RegisterService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterPageController {

    private final RegisterService registerService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public RegisterPageController(RegisterService registerService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.registerService = registerService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping("/registerPage")
    public String registerPage() {
        return "registerPage";
    }

    /*
    POJO 클래스(UserForm)를 이용하여 해당 데이터를 직접 사용하지 않고 POJO 클래스를 통해 추출하기 때문에 보안상의 이유로 권장되는 방법이다.

     */
    @PostMapping("/registerProc")
    public String registerProc(UserForm userForm) {
        User user = new User();

        String rawPassword = userForm.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);

        user.setUsername(userForm.getUsername());
        user.setPassword(encPassword);

        registerService.join(user);

        return "redirect:/";
    }
}
