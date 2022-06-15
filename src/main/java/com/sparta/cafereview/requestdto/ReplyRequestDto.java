package com.sparta.cafereview.requestdto;

import lombok.Data;

@Data
public class ReplyRequestDto {
    private Long cafeid;
    private String userid;
    private String reply;
    private String nickname;
}
