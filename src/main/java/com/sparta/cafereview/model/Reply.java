package com.sparta.cafereview.model;


import com.sparta.cafereview.requestdto.ReplyRequestDto;
import lombok.*;

import javax.persistence.*;


@Getter
@NoArgsConstructor
@Entity
public class Reply {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column(nullable = false)
    private String reply;
    @Column(nullable = false)
    private Long cafeId;
    @Column(nullable = false)
    private String nickname;
    @Column(nullable = false)
    private String userId;

    public Reply(ReplyRequestDto requestDto, String userid, Long cafeId, String reply){
        this.reply = reply;
        this.cafeId = cafeId;
        this.nickname = requestDto.getNickname();
        this.userId = userid;

    }

    public Reply(ReplyRequestDto replyRequestDto){
        this.userId = replyRequestDto.getUserid();
        this.cafeId = replyRequestDto.getCafeId();
        this.reply = replyRequestDto.getReply();
        this.nickname = replyRequestDto.getNickname();
    }


    public void update(ReplyRequestDto requestDto){
        this.reply = requestDto.getReply();
    }

}
