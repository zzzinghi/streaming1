package com.sparta.videoapi.dto.video;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequestDto {
    private String title;
    private Long userId;
    private int duration;   //비디오 길이 설정
}
/*
admin 관련된 정보를 RegisterRequestDto에 포함시킬 필요는 없다.
사용자의 역할은 서버에서 관리하고 있어서, 클라이언트로부터 별도로 admin 값을 받아올 필요 x
*/