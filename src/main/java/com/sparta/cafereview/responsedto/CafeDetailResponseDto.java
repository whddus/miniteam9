package com.sparta.cafereview.responsedto;

import com.sparta.cafereview.model.Cafe;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
@AllArgsConstructor
@Getter
public class CafeDetailResponseDto {
        private String coffeebeanname;
        private String cafename;
        private String imgUrl;
        private String cafereview;
        private String userid;
        private String nickname;
        private int likecafenumber;
        private List<CafeDetailReplyResponseDto> reply;


}
