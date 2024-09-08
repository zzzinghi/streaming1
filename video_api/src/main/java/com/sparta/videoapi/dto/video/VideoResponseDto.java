package com.sparta.videoapi.dto.video;

import com.sparta.userapi.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VideoResponseDto {
    private Long id;
    private String title;
    private User user;
    private int views;
    private int playTime;
    private int playCount;

}
