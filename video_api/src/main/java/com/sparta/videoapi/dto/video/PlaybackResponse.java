package com.sparta.videoapi.dto.video;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class PlaybackResponse {
    private Long videoId;
    private Long currentPosition;
    private int adPlayCount;

    //매개변수를 받는 생성자 추가
    public PlaybackResponse(Long videoId, Long currentPosition, int adPlayCount) {
        this.videoId = videoId;
        this.currentPosition = currentPosition;
        this.adPlayCount = adPlayCount;
    }
}
