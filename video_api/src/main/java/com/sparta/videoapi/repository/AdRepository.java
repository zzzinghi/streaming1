package com.sparta.videoapi.repository;

import com.sparta.videoapi.entity.Ad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdRepository extends JpaRepository<Ad, Integer> {
}
