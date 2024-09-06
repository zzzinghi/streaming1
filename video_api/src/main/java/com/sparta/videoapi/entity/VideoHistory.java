package com.sparta.videoapi.entity;

import com.sparta.userapi.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "video_history")
@Component
public class VideoHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne
    @JoinColumn(name ="video_id", nullable = false)
    private Video video;    //비디오와의 관계

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;      //사용자와의 관게

    @Column(nullable = false)
    private Timestamp lastPlayTime;     // 마지막 재생된 시간

    @Column(nullable = false)
    private Long currentPosition;    // 마지막 재생 위치

    public VideoHistory(User user, Video video, Long currentPosition) {
        this.user = user;
        this.video = video;
        this.currentPosition = currentPosition;
    }

}

