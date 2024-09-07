package com.sparta.videoapi.service;

import com.sparta.videoapi.entity.Ad;
import com.sparta.videoapi.repository.AdRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdService {

    private AdRepository adRepository;

    //광고 체크 및 시청 카운트 증가
//    public void checkAndCountAdViews(Long videoId, Long currentPosition) {
//        //비디오에 포함된 광고 리스트 가져오기
//        List<Ad> ads = adRepository.findByVideoId(videoId);
//
//        //광고가 재생된 지점까지 왔는지 확인 후 시청 횟수 증가
//        for (Ad ad : ads) {
//            // currentPosition이 광고를 처음 볼 때의 기준이 됨
//            //중단 후 재개할 때는 currentPosition 값을 사용해 이전에 중단한 지점부터 광고를 재생
//            if (currentPosition >= ad.getPlayPosition()) {  //광고의 재생 기준 시점
//                ad.incrementPlayCount();    // 광고 재생 횟수 증가
//                adRepository.save(ad);
//            }
//        }
//    }

    //광고 시청 카운트 로직 추가
    public void incrementAdview(Long adId, Long currentPosition) {
        Ad ad = adRepository.findById(adId)
                .orElseThrow(() -> new RuntimeException("Ad not found"));
        if(currentPosition >= ad.getPlayPosition()) {   //광고 재생 시점 도달 확인
            ad.incrementPlayCount();    // 시청 획수  증가
            adRepository.save(ad);
        }
    }


}
