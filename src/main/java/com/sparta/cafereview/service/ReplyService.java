package com.sparta.cafereview.service;

import com.sparta.cafereview.model.Reply;
import com.sparta.cafereview.repository.ReplyRepository;
import com.sparta.cafereview.requestdto.ReplyRequestDto;
import com.sparta.cafereview.responsedto.ReplyResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReplyService {
    private final ReplyRepository replyRepository;


    // 댓글 작성
    public boolean saveReply(ReplyRequestDto replyRequestDto) {
        Reply beforeSaveReply = new Reply(replyRequestDto);
        Reply savedReply = replyRepository.save(beforeSaveReply);
        Reply checkReply = replyRepository.findById(savedReply.getId()).orElse(null);

        if (savedReply.equals(checkReply)) {
            log.info("댓글 작성 완료하였습니다." + savedReply);
            return true;
        }
        log.info("댓글 작성 실패하였습니다." + beforeSaveReply);
        return false;
    }

    //댓글 전체 조회
    public List<ReplyResponseDto> getListReply(Long cafeid) {
        List<Reply> replies = replyRepository.findAllByCafeid(cafeid);
        return replies.stream().map(ReplyResponseDto::new).collect(Collectors.toList());
    }

    //댓글 전체 조회(페이징 적용)
    public Page<Reply> getListPagingReply(int page, int size, String sortBy, Long cafeid) {
        Sort sort = Sort.by(Sort.Direction.DESC, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return replyRepository.findAllByCafeidPage(cafeid, pageable);
    }


    // 댓글 수정
    public boolean update(Long cafeid, Long replyid, ReplyRequestDto replyRequestDto) {
        Reply reply = replyRepository.findByCafeidAndId(cafeid, replyid);

        if (reply.getUserid().equals(replyRequestDto.getUserid())) {
            reply.update(replyRequestDto);
            Long BeforeSaveReplyId = reply.getId();
            Reply savedReply = replyRepository.save(reply);
            log.info("댓글 수정 완료" + savedReply);
            return BeforeSaveReplyId.equals(savedReply.getId());
        }
        log.info("댓글 수정 실패" + reply);
        return false;
    }

    //댓글 삭제
    public boolean deleteReply(Long cafeid, Long replyid, String userid) {
        Reply reply = replyRepository.findByCafeidAndId(cafeid, replyid);

        if (reply.getUserid().equals(userid)) {
            replyRepository.deleteById(replyid);
            log.info("댓글 삭제 완료");
            return true;
        }
        log.info("댓글 삭제 실패" + reply);
        return false;
    }
}
