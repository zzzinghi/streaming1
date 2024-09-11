package com.sparta.videoapi.controller;

import com.sparta.videoapi.dto.ad.AdRequestDto;
import com.sparta.videoapi.dto.ad.AdResponseDto;
import com.sparta.videoapi.dto.ad.VideoAdRequestDto;
import com.sparta.videoapi.dto.ad.VideoAdResponseDto;
import com.sparta.videoapi.service.AdService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdController {

    private AdService adService;

    //광고 시청 횟수 증가 엔드포인트 -> 비디오 재생..(-)
    @PostMapping("/{adId}/view")
    public ResponseEntity<String> incrementAdview(@PathVariable int adId, @RequestBody Long currentPosition) {
       adService.incrementAdview(adId, currentPosition);
       return ResponseEntity.ok("Ad view count incremented");
    }
    //광고 등록
    @PostMapping("/addAd")
    public ResponseEntity<AdResponseDto> addAd(@RequestBody AdRequestDto adRequestDto) {
        return adService.addAd(adRequestDto);
    }

    //비디오 광고 등록
    @PostMapping("/{adId}/{videoId}")
    public ResponseEntity<VideoAdResponseDto> addVideoAd(@RequestBody VideoAdRequestDto videoAdRequestDto) {
        return adService.addVideoAd(videoAdRequestDto);
    }
}
