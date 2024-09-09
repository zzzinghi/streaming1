package com.sparta.videoapi.dto.video;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayRequestDto {
    private Long videoId;
    private Long userId;
}
