package com.sparta.userapi.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequestDto {
    private String user;
    private String password;
}
