package com.sparta.videoapi.dto.video;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequestDto {
    private String title;
    private Long userId;

}
