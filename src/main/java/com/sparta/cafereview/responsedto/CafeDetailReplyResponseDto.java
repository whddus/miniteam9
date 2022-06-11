package com.sparta.cafereview.responsedto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CafeDetailReplyResponseDto {
    private Long id;
    private String nickname;
    private String reply;

}
