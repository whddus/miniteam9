package com.sparta.cafereview.responsedto;

import com.sparta.cafereview.model.Reply;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CafeDetailReplyResponseDto {
    private Long id;
    private String nickname;
    private String reply;

    public CafeDetailReplyResponseDto(Reply reply){
        this.id = reply.getId();
        this.nickname = reply.getNickname();
        this.reply = reply.getReply();
    }

}
