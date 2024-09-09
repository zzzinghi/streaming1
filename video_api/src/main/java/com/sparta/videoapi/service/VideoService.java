package com.sparta.videoapi.service;

import com.sparta.userapi.entity.User;
import com.sparta.userapi.repository.UserRepository;
import com.sparta.videoapi.dto.video.*;
import com.sparta.videoapi.entity.Video;
import com.sparta.videoapi.entity.VideoHistory;
import com.sparta.videoapi.repository.VideoHistoryRepository;
import com.sparta.videoapi.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    /* 1. 새로운 엔티티이므로 new Video()로 생성 후 필요한 정보를 설정하고 저장
            -> 새로운 앤티티 - db에 저장되지 않은 새로운 객체를 생성한다는 뜻.
            즉, 비디오 자체에 대한 앤티티는 정의되어 있지만, 개별 비디오 객체는 아직 db에 존재하지 않기 때문에,
                새로운 객체를 만들어 db에 저장해야 한다는 의미.

      1. getTitle 이고, user는 그냥 user인 이유
        -> getTitle은 클라이언트가 요청한 비디오 제목 (dto에서 추출한 값)
        -> user는 데이터베이스에서 조회한 user 앤티티 객체로, 클라이언트가 보낸 userId를 기반으로 조회한 실제 사용자
    * */
    //비디오 등록 메서드
    public ResponseEntity<RegisterResponseDto> registerVideo(RegisterRequestDto registerRequestDto) {
        User user = userRepository.findById(registerRequestDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Video video = new Video(registerRequestDto.getTitle(), user);  //1.
        videoRepository.save(video);

        //RegisterResponseDto 생성
        RegisterResponseDto registerResponseDto = new RegisterResponseDto(video.getTitle(), video.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(registerResponseDto);
    }

    //비디오 재생 로직
    public ResponseEntity<PlayResponseDto> playVideo(PlayRequestDto playRequestDto) {
        // 비디오 조회
        Video video = videoRepository.findById(playRequestDto.getVideoId())
                .orElseThrow(() -> new RuntimeException("Video not found"));
        /* 앤티티랑 연결 된 videoRepository에서 findById로 videoId를 찾아서 video 객체를 만들어줌. 그리고 못 찾을 시 예외 발생 시킴 */

        //사용자 진행 기록 조회
        Optional<VideoHistory> videoHistoryOpt = videoHistoryRepository.findByUserIdAndVideoId(playRequestDto.getUserId(), playRequestDto.getVideoId());
        /*엔티티랑 연결 돼 있는 videoHistoryRepository에서  findByUserIdAndVideoId(?)를 만들어서 userId랑 videoId를 찾아서 videoHistoryOpt 객체에 넣어줌 */

        //조회수 및 재생 횟수 증가
        video.incrementViews();
        video.incrementPlayTime(); //재생 횟수 증가
        videoRepository.save(video);

        //시작 위치 설정 (이전 기록이 있으면 그 지점에서 재생)
        long currentPosition = videoHistoryOpt.map(VideoHistory::getCurrentPosition).orElse(0L);

        //광고 체크 및 시청 카운트 증가
//        adService.checkAndCountAdViews(videoId, currentPosition); //광고 재생 체크 로직 추가

        PlayResponseDto playResponseDto = new PlayResponseDto(video.getId(), currentPosition, video.getPlayCount());

        //재생 응답 반환
        return ResponseEntity.ok(playResponseDto);
    }

    //비디오 중단 시 재생 위치 저장
    public ResponseEntity<SaveStopResponseDto> saveCurrentPosition(SaveStopRequestDto saveStopRequestDto) {
        //userId를 사용하여 User  객체를 조회
        User user = userRepository.findById(saveStopRequestDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        //videoId를 사용하여 video 객체를 조회
        Video video = videoRepository.findById(saveStopRequestDto.getVideoId())
                .orElseThrow(() -> new RuntimeException("Video not found"));
        //videoHistory 조회 또는 새로운 videoHistory 생성
        VideoHistory videoHistory = videoHistoryRepository.findByUserIdAndVideoId(saveStopRequestDto.getUserId(), saveStopRequestDto.getVideoId())
                .orElse(new VideoHistory(user, video, saveStopRequestDto.getCurrentPosition()));
        //현재 위치 설정
        videoHistory.setCurrentPosition(saveStopRequestDto.getCurrentPosition());
        //videoHistory
        videoHistoryRepository.save(videoHistory);

        SaveStopResponseDto saveStopResponseDto = new SaveStopResponseDto(
                saveStopRequestDto.getVideoId(),
                saveStopRequestDto.getUserId(),
                videoHistory.getCurrentPosition());

        return ResponseEntity.ok(saveStopResponseDto);
    }

    //비디오 조회


}
