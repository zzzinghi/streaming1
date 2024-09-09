package com.sparta.videoapi.dto.video;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveStopRequestDto {
    private Long userId;
    private Long videoId;
    private Long currentPosition;

}
