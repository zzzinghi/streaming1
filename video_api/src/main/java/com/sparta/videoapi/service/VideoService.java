package com.sparta.videoapi.service;

import com.sparta.userapi.entity.User;
import com.sparta.userapi.repository.UserRepository;
import com.sparta.videoapi.dto.VideoPlaybackResponse;
import com.sparta.videoapi.entity.Video;
import com.sparta.videoapi.entity.VideoHistory;
import com.sparta.videoapi.repository.AdRepository;
import com.sparta.videoapi.repository.VideoHistoryRepository;
import com.sparta.videoapi.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class VideoService {

    private final UserRepository userRepository;

    private final VideoRepository videoRepository;

    private final VideoHistoryRepository videoHistoryRepository;

    private final AdService adService;

    @Autowired
    public VideoService(UserRepository userRepository, VideoRepository videoRepository, VideoHistoryRepository videoHistoryRepository, AdService adService) {
        this.userRepository = userRepository;
        this.videoRepository = videoRepository;
        this.videoHistoryRepository = videoHistoryRepository;
        this.adService = adService;
    }

    //비디오 재생 로직
    public VideoPlaybackResponse playVideo(Long videoId, Long userId) {
        // 비디오 조회
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new RuntimeException("Video not found"));

        //사용자 진행 기록 조회
        Optional<VideoHistory> videoHistoryOpt = videoHistoryRepository.findByUserIdAndVideoId(userId, videoId);

        //조회수 및 재생 횟수 증가
        video.incrementViews();
        video.incrementPlayTime(); //재생 횟수 증가
        videoRepository.save(video);

        //시작 위치 설정 (이전 기록이 있으면 그 지점에서 재생)
        long currentPosition = videoHistoryOpt.map(VideoHistory::getCurrentPosition).orElse(0L);

        //광고 체크 및 시청 카운트 증가
//        adService.checkAndCountAdViews(videoId, currentPosition); //광고 재생 체크 로직 추가

        //재생 응답 반환
        return new VideoPlaybackResponse(videoId, currentPosition, video.getPlayCount());
    }

    //비디오 중단 시 재생 위치 저장
    public void saveCurrentPosition(Long userId, Long videoId, Long currentPosition) {
        //userId를 사용하여 User  객체를 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        //videoId를 사용하여 video 객체를 조회
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new RuntimeException("Video not found"));
        //videoHistory 조회 또는 새로운 videoHistory 생성
        VideoHistory videoHistory = videoHistoryRepository.findByUserIdAndVideoId(userId, videoId)
                .orElse(new VideoHistory(user, video, currentPosition));
        //현재 위치 설정
        videoHistory.setCurrentPosition(currentPosition);
        //videoHistory
        videoHistoryRepository.save(videoHistory);
    }


}
