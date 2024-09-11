package com.sparta.videoapi.dto.ad;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VideoAdResponseDto {
    private Long videoId;
    private Long AdId;
    private String AdName;
    private String title;
    private int adPlayCount;

    public VideoAdResponseDto(Long videoId, String title, int adPlayCount) {
        this.videoId = videoId;
        this.title = title;
        this.adPlayCount = adPlayCount;
    }
}
