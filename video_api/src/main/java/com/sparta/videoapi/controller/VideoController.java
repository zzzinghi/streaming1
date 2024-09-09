package com.sparta.videoapi.controller;

import com.sparta.videoapi.dto.video.*;
import com.sparta.videoapi.service.VideoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/videos")
public class VideoController {

    private VideoService videoService;

    //비디오 등록 앤드포인트
    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDto> registerVideo(@RequestBody RegisterRequestDto registerRequestDto) {
      return videoService.registerVideo(registerRequestDto);
    }

    //비디오 재생 엔드포인트
    @GetMapping("/{videoId}/play")
    public ResponseEntity<PlayResponseDto> playVideo(@PathVariable PlayRequestDto playRequestDto) {
        return videoService.playVideo(playRequestDto);
    }

    //비디오 중단 시점 저장 엔드포인트
    @PostMapping("/{videoId}/stop")
    public ResponseEntity<SaveStopResponseDto> saveCurrentPosition(@PathVariable SaveStopRequestDto saveStopRequestDto) {
        return videoService.saveCurrentPosition(saveStopRequestDto);
    }

}
