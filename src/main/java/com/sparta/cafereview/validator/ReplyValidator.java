package com.sparta.cafereview.validator;

import com.sparta.cafereview.requestdto.ReplyRequestDto;

public class ReplyValidator {

    public static void validateReplyInput(ReplyRequestDto replyRequestDto){
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
