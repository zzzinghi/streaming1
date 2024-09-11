package com.sparta.videoapi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "video_ad")
public class VideoAd {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "video_id", nullable = false)
    private Video video;    //비디오와의 관계

    @ManyToOne
    @JoinColumn(name = "ad_id", nullable = false)
    private Ad ad;      //광고와의 관계


    public VideoAd(Video video, Ad ad) {
        this.video = video;
        this.ad = ad;
    }
}
