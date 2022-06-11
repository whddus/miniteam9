package com.sparta.cafereview.model;


import com.sparta.cafereview.requestdto.ReplyRequestDto;
import lombok.*;

import javax.persistence.*;


@Getter
@NoArgsConstructor
@Entity
public class Reply {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;
    @Column(nullable = false)
    private String reply;
    @Column(nullable = false)
    private Long cafeId;
    @Column(nullable = false)
    private String nickname;
    @Column(nullable = false, unique = true)
    private String userId;

    public Reply(ReplyRequestDto requestDto, String userid, Long cafeId, String reply){
        this.reply = reply;
        this.cafeId = cafeId;
        this.nickname = requestDto.getNickname();
        this.userId = userid;

    }

    public Reply(ReplyRequestDto requestDto, String userid, Long cafeId){
        this.cafeId = cafeId;
        this.reply = requestDto.getReply();
        this.nickname = requestDto.getNickname();
        this.userId = userid;

    }


    public void update(ReplyRequestDto requestDto){
        this.reply = requestDto.getReply();
    }

}
