package com.sparta.videoapi.repository;

import com.sparta.videoapi.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;


public interface VideoRepository extends JpaRepository<Video, Long> {
    // 추가적으로 필요하면 커스텀 메서드도 선언 가능
    // 데이터베이스와의 인터페이스 역할을 하며, 데이터를 저장하거나 불러오는 작업을 처리

    //service는 비즈니스 로직을 담고 있음,
    //이 로직을 처리하기 위해 필요한 데이터를 repository에서 불러오거나 저장함


}
