package com.sparta.videoapi.dto.ad;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VideoAdRequestDto {
    private Long videoId;
    private Long AdId;
    private String AdName;
    private String title;
}
