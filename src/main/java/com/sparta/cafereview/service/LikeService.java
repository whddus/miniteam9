package com.sparta.cafereview.service;

import com.sparta.cafereview.model.Cafe;
import com.sparta.cafereview.model.Likes;
import com.sparta.cafereview.repository.CafeRepository;
import com.sparta.cafereview.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LikeService {
    private final LikeRepository likeRepository;
    private final CafeRepository cafeRepository;

    //좋아요 하기
    public boolean like(Long cafeid, String userid){
        Likes likecheck = likeRepository.findByUseridAndCafeid(userid, cafeid).orElse(null);
        if(likecheck != null){
            return false;
        }
        Cafe likeupcafe = cafeRepository.findById(cafeid).orElse(null);
        likeupcafe.likecafe();
        Likes likeup = new Likes(cafeid, userid);
        likeRepository.save(likeup);
        return true;
    }

    //좋아요 취소
    public boolean unlike(Long cafeid, String userid){
        Likes likecheck = likeRepository.findByUseridAndCafeid(userid, cafeid).orElse(null);
        if(likecheck != null){
            Cafe unlikeupcafe = cafeRepository.findById(cafeid).orElse(null);
            unlikeupcafe.unlikecafe();
            likeRepository.delete(likeRepository.findByUseridAndCafeid(userid, cafeid).orElseThrow(
                    () -> new NullPointerException("좋아요를 하지 않았습니다.")));
            return true;
        }
        return false;
    }

    //좋아요 조회
    public boolean likecheck(Long cafeid, String userid){
        Likes likecheck = likeRepository.findByUseridAndCafeid(userid, cafeid).orElse(null);
        return likecheck == null;
    }
}
