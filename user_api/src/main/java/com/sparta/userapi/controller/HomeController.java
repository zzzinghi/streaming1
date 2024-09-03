package com.sparta.userapi.controller;

import com.sparta.userapi.security.UserDetailsImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        model.addAttribute("username", userDetailsImpl.getUsername());
        return "index";
    }
}