package com.sparta.userapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordConfig {   //passwordConfig

    @Bean
    public PasswordEncoder passwordEncoder() {  //bean으로 등록한 객체를 반환하는 메서드를 선언해줌
        //Bean으로 등록할 때도, passwordEncoder 이렇게 bean으로 등록되서 -> 가져다가 사용하면,
        // 즉 DI(Dependency Injection)을 주입 받으면 BCryptPasswordEncoder() 구현체가 등록(주입)된다..
        //BCrypt가 무엇인가?? Hash 함수이다(비밀번호를 암호화 해주는)
        //왜 비밀번호를 암호화해야 되는지, BCryptPasswordEncoder로 encoding해야 되는지,..
        return new BCryptPasswordEncoder();     //passwordEncoder 중에서  BCryptPasswordEncoder 이걸 선택했다??
        //
    }
}   //이렇게 작성해주면 spring 서버가 뜰 때 spring Ioc Container에 이게 Bean으로 저장될 것이다.
//bean으로 저장될 때 어떻게 저장되지?
//class PasswordConfig 에서 class bean으롣 등록할 때 대문자가 아니라 소문자 p로 바뀌면서
//passwordConfig 이런식으로 저장됨

/* 위와 같이 설정하면 spring 서버가 뜰 때 spring ioc contai
*/