package com.sparta.userapi.controller;

import com.sparta.userapi.entity.User;
import com.sparta.userapi.security.UserDetailsImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class ProductController {

    @GetMapping("/products")
    public String getProducts(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        //Authentication 의 principle
        User user = userDetailsImpl.getUser();
        System.out.println("User.getUsername() = "+ user.getUsername() );

        return "redirect:/";
    }
}