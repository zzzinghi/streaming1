package com.sparta.videoapi.controller;

import com.sparta.videoapi.service.AdService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdController {

    private AdService adService;

    //광고 시청 횟수 증가 엔드포인트
    @PostMapping("/{adId}/view")
    public ResponseEntity<String> incrementAdview(@PathVariable Long adId, @RequestBody Long currentPosition) {
       adService.incrementAdview(adId, currentPosition);
       return ResponseEntity.ok("Ad view count incremented");
    }

}
