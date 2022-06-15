package com.sparta.cafereview.responsedto;

import com.sparta.cafereview.model.Likes;

public class LikeResponseDto {
    private Long cafeid;
    private String userid;

    public LikeResponseDto(Likes likes){
        this.cafeid = likes.getCafeid();
        this.userid = likes.getUserid();
    }
}
