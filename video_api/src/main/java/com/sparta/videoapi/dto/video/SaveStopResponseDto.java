package com.sparta.videoapi.dto.video;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaveStopResponseDto {
    private Long videoId;
    private Long userId;
    private Long currentPosition;
}
