package com.sparta.cafereview.requestdto;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class CafeRequestDto {
    private String cafename;
    private String coffeebeanname;
    private String imageUrl;
    private String cafereview;
    private String userid;
}
