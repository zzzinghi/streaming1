package com.sparta.videoapi.repository;

import com.sparta.videoapi.entity.Video;
import com.sparta.videoapi.entity.VideoAd;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface VideoAdRepository extends JpaRepository<VideoAd, Long> {
    List<VideoAd> findByVideoId(Long videoId);


}
