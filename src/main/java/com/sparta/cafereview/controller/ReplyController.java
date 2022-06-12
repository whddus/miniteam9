package com.sparta.cafereview.controller;

import com.sparta.cafereview.requestdto.ReplyRequestDto;
import com.sparta.cafereview.responsedto.ReplyResponseDto;
import com.sparta.cafereview.security.UserDetailsImpl;
import com.sparta.cafereview.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/cafe")
@RequiredArgsConstructor
@RestController
public class ReplyController {

    private final ReplyService replyService;

    // 댓글 전체 조회
    @GetMapping("/{cafeid}/reply/list")
    private List<ReplyResponseDto> getReply(@PathVariable Long cafeid) {
        return replyService.getReply(cafeid);
    }

    //댓글 작성(userDetails 정보 필요)
    @PostMapping("/{cafeid}/reply/save")
    public boolean createReply(@PathVariable Long cafeid,
                               @RequestBody ReplyRequestDto replyRequestDto,
                               @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (cafeid != null) {
            String userid = userDetails.getUsername();
            String nickname = userDetails.getNickname();
            replyRequestDto.setUserid(userid);
            replyRequestDto.setNickname(nickname);
            replyRequestDto.setCafeId(cafeid);
            replyService.createReply(replyRequestDto);
            return true;
        }
        log.info("cafaid is null");
        return false;
    }

    //댓글 수정(userDetails 정보 필요)
    @PatchMapping("/{cafeid}/reply/{replyid}/update")
    public boolean updateReply(@PathVariable Long cafeid, @PathVariable Long replyid,
                               @RequestBody ReplyRequestDto requestDto,
                               @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (cafeid != null && replyid != null) {
            Long userId = 1L;
            String nickname = "tempNickname";
            replyService.update(replyid, requestDto, nickname, userId, cafeid);
            return true;
        }
        log.info("cafeid and replyid are null");
        return false;
    }

    //댓글 삭제(userDetails 정보 필요)
    @DeleteMapping("{cafeid}/reply/{replyid}/delete")
    public boolean deleteReply(@PathVariable Long replyid,
                               @PathVariable Long cafeid,
                               @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (replyid != null) {
            Long userId = 1L;
            replyService.deleteReply(replyid, userId, cafeid);
            return true;
        }
        log.info("replyid is null.");
        return false;
    }

}
