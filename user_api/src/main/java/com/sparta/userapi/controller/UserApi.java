package com.sparta.userapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/v1")
public class UserApi {

    @GetMapping("test")
    public String test() {
        return "Hello, User API!";
    }

}
