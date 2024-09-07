package com.sparta.videoapi.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "ad")
public class Ad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String adName;      //광고 제목

    private int adPlayCount = 0;    //광고 재생 횟수

    private int playPosition;

    // 광고 재생 횟수 증가 메서드
    public void incrementPlayCount() {
        this.adPlayCount += 1;
    }
}

