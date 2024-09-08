package com.sparta.videoapi.dto.video;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterResponseDto {
    private String title;
    private Long userId;

    public RegisterResponseDto(String title, Long userId) {
        this.title = title;
        this.userId = userId;
    }

}
