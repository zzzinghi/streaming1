package com.sparta.userapi.controller;

import com.sparta.userapi.dto.LoginRequestDto;
import com.sparta.userapi.dto.SignupRequestDto;
import com.sparta.userapi.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class UserController {


    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/login-page")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/user/signup")
    public String signupPage() {
        return "signup";
    }

    @PostMapping("/user/singup")
    public String singup(SignupRequestDto requestDto) {
       userService.signup(requestDto);

       return "redirect:/user/login-page";
    }

    @PostMapping("/user/login")
    public String login(LoginRequestDto loginRequestDto, HttpServletResponse res) {
        try {
            userService.login(loginRequestDto, res);
        } catch (Exception e) {
            return "redirect:/user/login-page?error";
        }

        return "redirect:/";        //redirect! / (슬래시)는 main으로!
    }

}