package com.sparta.videoapi.entity;

import jakarta.persistence.*;
import com.sparta.userapi.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor(force = true)
@Table(name = "video")
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne  /* msa 구조인데, manyToOne 해서 다른 모듈의 테이블을 연결하는 게 맞나??*/
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private int views = 0;  //시청 횟수

    @Column(nullable = false)
    private int playTime; //재생 시간

    @Column(nullable = false)
    private int playCount = 0; //재생 횟수

    @Column(nullable = false)
    private int duration; // 비디오 길이 (초 단위)

    //조회수 증가 메서드
    public void incrementViews() { this.views += 1; }

    //재생 횟수 증가 메서드
    public void incrementPlayTime() {
        this.playCount += 1;
    }

    //생성자 추가
    public Video(String title, User user) {
        this.title = title;
        this.user = user;
    }
}
