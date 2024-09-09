package com.sparta.videoapi.dto.video;

import com.sparta.videoapi.entity.Video;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ViewResponseDto {
    private String title;
    private Long user;
    private Video video;

    public ViewResponseDto(Video video) {
        this.title = video.getTitle();
        this.user = video.getUser().getId();
        this.video = video;
    }
}
