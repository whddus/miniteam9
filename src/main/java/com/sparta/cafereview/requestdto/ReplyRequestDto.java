package com.sparta.cafereview.requestdto;

import lombok.Data;

@Data
public class ReplyRequestDto {
    private String userid;
    private String reply;
    private String nickname;
    private Long cafeId;
}
