package com.sparta.videoapi.service;

import com.sparta.videoapi.dto.ad.AdRequestDto;
import com.sparta.videoapi.dto.ad.AdResponseDto;
import com.sparta.videoapi.dto.ad.VideoAdRequestDto;
import com.sparta.videoapi.dto.ad.VideoAdResponseDto;
import com.sparta.videoapi.dto.video.VideoResponseDto;
import com.sparta.videoapi.entity.Ad;
import com.sparta.videoapi.entity.Video;
import com.sparta.videoapi.entity.VideoAd;
import com.sparta.videoapi.repository.AdRepository;
import com.sparta.videoapi.repository.VideoAdRepository;
import com.sparta.videoapi.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdService {

    public final AdRepository adRepository;
    private final VideoRepository videoRepository;
    private VideoAdRepository videoAdRepository;

    @Autowired
    public AdService(VideoAdRepository videoAdRepository, AdRepository adRepository, VideoRepository videoRepository) {
        this.videoAdRepository = videoAdRepository;
        this.adRepository = adRepository;
        this.videoRepository = videoRepository;
    }

    //광고 등록
    @Transactional
    public ResponseEntity<AdResponseDto> addAd(AdRequestDto adRequestDto) {
        Ad ad = new Ad(adRequestDto.getAdName(), adRequestDto.getPlayPosition());
        adRepository.save(ad);

        AdResponseDto adResponseDto = new AdResponseDto(ad.getId(), ad.getAdName(), ad.getPlayPosition());
        return ResponseEntity.ok(adResponseDto);
    }

    //광고 체크 및 시청 카운트 증가
    public void checkAndCountAdViews(Long videoId, Long currentPosition) {
        List<VideoAd> videoAds = videoAdRepository.findByVideoId(videoId);  //인스턴스 사용
        for (VideoAd videoAd : videoAds) {
            if (currentPosition >= videoAd.getAd().getPlayPosition()) {
                videoAd.getAd().incrementPlayCount(); // 광고 재생 횟수 증가
                videoAdRepository.save(videoAd);
            }
        }
    }

    //광고 시청 카운트 로직 추가
    public void incrementAdview(int adId, Long currentPosition) {
        Ad ad = adRepository.findById(adId)
                .orElseThrow(() -> new RuntimeException("Ad not found"));
        if (currentPosition >= ad.getPlayPosition()) {   //광고 재생 시점 도달 확인
            ad.incrementPlayCount();    // 시청 획수  증가
            adRepository.save(ad);
        }
    }

    //비디오 광고 등록
    public ResponseEntity<VideoAdResponseDto> addVideoAd(VideoAdRequestDto videoAdRequestDto) {
        Video video = videoRepository.findById(videoAdRequestDto.getVideoId())
                .orElseThrow(() -> new IllegalArgumentException("not found video"));
        Ad ad = adRepository.findById(videoAdRequestDto.getAdId().intValue())
                .orElseThrow(() -> new IllegalArgumentException("not found ad"));
        VideoAd videoAd = new VideoAd(video, ad);
        videoAdRepository.save(videoAd);
        VideoAdResponseDto videoAdResponseDto = new VideoAdResponseDto(
                video.getId(),
                video.getTitle(),
                ad.getAdPlayCount());

       return ResponseEntity.ok(videoAdResponseDto);
    }
}
