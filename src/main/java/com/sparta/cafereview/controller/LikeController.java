package com.sparta.cafereview.controller;

import com.sparta.cafereview.security.UserDetailsImpl;
import com.sparta.cafereview.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class LikeController {
    private final LikeService likeService;

    //좋아요 하기
    @GetMapping("/like/{cafeid}")
    public boolean like(@PathVariable Long cafeid,
                        @AuthenticationPrincipal UserDetailsImpl userDetails){
        String userid = userDetails.getUsername();
        return likeService.like(cafeid, userid);
    }

    //좋아요 취소
    @GetMapping("/unlike/{cafeid}")
    public boolean unlike(@PathVariable Long cafeid,
                          @AuthenticationPrincipal UserDetailsImpl userDetails){
        String userid = userDetails.getUsername();
        return likeService.unlike(cafeid, userid);
    }
}
