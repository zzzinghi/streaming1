package com.sparta.videoapi.dto.ad;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdResponseDto {
    private int id;
    private String adName;
    private int playPosition;

    //리스폰스로 playPosition 을 줄 필요가 있나?
    public AdResponseDto(int id, String adName, int playPosition) {
        this.id = id;
        this.adName = adName;
        this.playPosition = playPosition;
    }
}
