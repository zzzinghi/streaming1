package com.sparta.videoapi.dto;

import com.sparta.videoapi.entity.Video;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class VideoPlaybackResponse {
    private Long videoId;
    private Long currentPosition;
    private int adPlayCount;

    //매개변수를 받는 생성자 추가
    public VideoPlaybackResponse(Long videoId, Long currentPosition, int adPlayCount) {
        this.videoId = videoId;
        this.currentPosition = currentPosition;
        this.adPlayCount = adPlayCount;
    }
}
