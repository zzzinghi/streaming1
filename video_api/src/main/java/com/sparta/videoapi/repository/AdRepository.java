package com.sparta.videoapi.repository;

import com.sparta.videoapi.entity.Ad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AdRepository extends JpaRepository<Ad, Long> {
    List<Ad> findByVideoId(Long videoId);

}
