package com.sparta.videoapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = {"com.sparta.videoapi", "com.sparta.userapi"})	//패키지 스캔 범위에 userRepository 가 포함된 패키지를 추가해야 함
@EntityScan(basePackages = {"com.sparta.userapi.entity", "com.sparta.videoapi.entity"})
public class VideoApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(VideoApiApplication.class, args);
	}

}
