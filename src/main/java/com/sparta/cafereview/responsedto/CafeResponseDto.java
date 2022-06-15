package com.sparta.cafereview.responsedto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CafeResponseDto {
    private Long id;
    private String coffeebeanname;
    private String cafename;
    private String imgUrl;
    private String cafereview;
    private String userid;
    private String nickname;
    private int likecafenumber;
}
