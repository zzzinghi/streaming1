package com.sparta.videoapi.controller;

import com.sparta.videoapi.dto.VideoPlaybackResponse;
import com.sparta.videoapi.service.VideoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/videos")
public class VideoController {

    private VideoService videoService;

    //비디오 재생 엔드포인트
    @GetMapping("/{videoId}/play")
    public ResponseEntity<VideoPlaybackResponse> playVideo(@PathVariable Long videoId, Long userId) {
        VideoPlaybackResponse playbackResponse = videoService.playVideo(videoId, userId);
        return ResponseEntity.ok(playbackResponse);
    }

    //비디오 중단 시점 저장 엔드포인트
    @PostMapping("/{videoId}/stop")
    public ResponseEntity<String> stopVideo(@PathVariable Long videoId, @RequestParam Long userId, @RequestParam Long currentPosition) {
        videoService.saveCurrentPosition(videoId, userId, currentPosition);
        return ResponseEntity.ok("Playback position saved");
    }

}
