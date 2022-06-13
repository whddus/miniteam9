package com.sparta.cafereview.service;

import com.sparta.cafereview.model.Reply;
import com.sparta.cafereview.repository.ReplyRepository;
import com.sparta.cafereview.requestdto.ReplyRequestDto;
import com.sparta.cafereview.responsedto.ReplyResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReplyService {
    private final ReplyRepository replyRepository;

    // 댓글 조회
    public List<ReplyResponseDto> getReply(Long cafeid) {
        List<Reply> replies = replyRepository.findAllByCafeid(cafeid);
        List<ReplyResponseDto> list = replies.stream().map(ReplyResponseDto::new).collect(Collectors.toList());
        return list;
    }
    // 댓글 작성
    public boolean createReply(ReplyRequestDto replyRequestDto){
        //저장 유무를 판단 (영속성 컨텍스트 확인)
        Reply beforeSaveReply = new Reply(replyRequestDto);
        Reply SaveReply = replyRepository.save(beforeSaveReply);
        log.info("댓글 작성 완료하였습니다." + SaveReply);
        return true;
    }


    // 댓글 업데이트
    public boolean update(Long replyid, ReplyRequestDto requestDto, String nickname, String userid, Long cafeid){
        Reply reply = replyRepository.findById(replyid).orElseThrow(
                () -> new IllegalArgumentException("댓글이 존재하지 않습니다.")
        );
        String writerId = reply.getUserid();
        if(Objects.equals(writerId, userid)){
            reply.update(requestDto);
            replyRepository.save(reply);
            log.info("댓글 수정 완료" + reply);
            return true;
        }
        log.info("작성한 유저가 아닙니다.");
        return false;
    }
    //댓글 삭제
    public boolean deleteReply(Long replyid, String userid, Long cafeid){
        String writeId = replyRepository.findById(replyid).orElseThrow(
                () -> new IllegalArgumentException("댓글이 존재하지 않습니다.")
        ).getUserid();
        if(Objects.equals(writeId, userid)){
            replyRepository.deleteById(replyid);
            List<Reply> replies = replyRepository.findAllByCafeid(cafeid);
            log.info("댓글 삭제 완료" + replies);
            return true;
        }
        log.info("작성한 유저가 아닙니다.");
        return false;
    }
}
