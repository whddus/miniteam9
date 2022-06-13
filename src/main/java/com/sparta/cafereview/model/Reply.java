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
    private Long cafeid;
    @Column(nullable = false)
    private String nickname;
    @Column(nullable = false)
    private String userid;

    public Reply(ReplyRequestDto requestDto, String userid, Long cafeid, String reply){
        this.reply = reply;
        this.cafeid = cafeid;
        this.nickname = requestDto.getNickname();
        this.userid = userid;

    }

    public Reply(ReplyRequestDto replyRequestDto){
        this.userid = replyRequestDto.getUserid();
        this.cafeid = replyRequestDto.getCafeid();
        this.reply = replyRequestDto.getReply();
        this.nickname = replyRequestDto.getNickname();
    }


    public void update(ReplyRequestDto requestDto){
        this.reply = requestDto.getReply();
    }

}
