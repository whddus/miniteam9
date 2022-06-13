package com.sparta.cafereview.requestdto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CafeUpdateDto {
    private String cafename;
    private String coffeebeanname;
    private String imgUrl;
    private String cafereview;
}
