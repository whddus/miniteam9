package com.sparta.cafereview.validator;

import com.sparta.cafereview.requestdto.ReplyRequestDto;

public class ReplyValidator {

    public static void validateReplyInput(ReplyRequestDto replyRequestDto){
        //ID 검사
        if(replyRequestDto.getUserid() == null){
            throw new IllegalArgumentException("아이디를 확인해 주세요");
        }
        if(replyRequestDto.getUserid().equals("")){
            throw new IllegalArgumentException("아이디를 확인해 주세요");
        }

        //리뷰 번호 검사
        if(replyRequestDto.getCafeid() == null){
            throw new IllegalArgumentException("리뷰가 존재하지 않습니다");
        }
        if(replyRequestDto.getCafeid() == 0L){
            throw new IllegalArgumentException("리뷰가 존재하지 않습니다");
        }

        //내용 검사
        if(replyRequestDto.getReply() == null){
            throw new IllegalArgumentException("댓글 내용을 입력해 주세요");
        }
        if(replyRequestDto.getReply().equals("")){
            throw new IllegalArgumentException("댓글 내용을 입력해 주세요");
        }
        if(replyRequestDto.getReply().length() > 200){
            throw new IllegalArgumentException("댓글 내용은 200자를 넘길 수 없습니다");
        }

        //닉네임 검사
        if(replyRequestDto.getNickname() == null){
            throw new IllegalArgumentException("닉네임을 확인해 주세요");
        }
        if(replyRequestDto.getNickname().equals("")){
            throw new IllegalArgumentException("닉네임을 확인해 주세요");
        }
    }

    public static void validateUnDateReplyInput(ReplyRequestDto replyRequestDto){
        //내용 검사
        if(replyRequestDto.getReply() == null){
            throw new IllegalArgumentException("댓글 내용을 입력해 주세요");
        }
        if(replyRequestDto.getReply().equals("")){
            throw new IllegalArgumentException("댓글 내용을 입력해 주세요");
        }
        if(replyRequestDto.getReply().length() > 200){
            throw new IllegalArgumentException("댓글 내용은 200자를 넘길 수 없습니다");
        }
    }
}
