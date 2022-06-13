package com.sparta.cafereview.responsedto;

import com.sparta.cafereview.model.Reply;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class ReplyResponseDto {
    private Long id;

    private String nickname;
    private String userid;

    private String reply;

    public ReplyResponseDto(Reply reply){
        this.id = reply.getId();
        this.nickname = reply.getNickname();
        this.userid = reply.getUserid();
        this.reply = reply.getReply();
    }
}
