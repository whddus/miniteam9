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
        private String imageUrl;
        private String cafereview;
        private List<CafeDetailReplyResponseDto> reply;


}
