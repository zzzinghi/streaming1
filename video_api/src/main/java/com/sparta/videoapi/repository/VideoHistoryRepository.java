package com.sparta.videoapi.repository;

import com.sparta.videoapi.entity.VideoHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VideoHistoryRepository extends JpaRepository<VideoHistory, Long> {
    Optional<VideoHistory> findByUserIdAndVideoId(Long userId, Long videoId);
    //optional = null을 처리할 때 사용
}
