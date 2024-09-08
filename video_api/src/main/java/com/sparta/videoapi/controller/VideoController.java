package com.sparta.videoapi.controller;

import com.sparta.videoapi.dto.video.PlaybackResponse;
import com.sparta.videoapi.dto.video.RegisterRequestDto;
import com.sparta.videoapi.dto.video.RegisterResponseDto;
import com.sparta.videoapi.entity.Video;
import com.sparta.videoapi.service.VideoService;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<PlaybackResponse> playVideo(@PathVariable Long videoId, Long userId) {
        PlaybackResponse playbackResponse = videoService.playVideo(videoId, userId);
        return ResponseEntity.ok(playbackResponse);
    }

    //비디오 중단 시점 저장 엔드포인트
    @PostMapping("/{videoId}/stop")
    public ResponseEntity<String> stopVideo(@PathVariable Long videoId, @RequestParam Long userId, @RequestParam Long currentPosition) {
        videoService.saveCurrentPosition(videoId, userId, currentPosition);
        return ResponseEntity.ok("Playback position saved");
    }

}
