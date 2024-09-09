package com.sparta.userapi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {
    private String user;
    private String password;
    private String email;
//    private String kakao_id;
    private boolean admin = false;
    private String adminToken = "";
}