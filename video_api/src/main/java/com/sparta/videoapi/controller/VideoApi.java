package com.sparta.videoapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/video/v1")
public class VideoApi {

    @GetMapping("test")
    public String test() {
        return "video api test";
    }
}
