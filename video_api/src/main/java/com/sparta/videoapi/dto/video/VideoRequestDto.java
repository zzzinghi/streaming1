package com.sparta.videoapi.dto.video;

import com.sparta.userapi.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VideoRequestDto {

    private User user;
    private String title;
    private int views;
    private int playTime;
    private int playCount;

}
