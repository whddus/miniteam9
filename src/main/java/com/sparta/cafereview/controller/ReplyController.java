package com.sparta.cafereview.controller;

import com.sparta.cafereview.model.Reply;
import com.sparta.cafereview.requestdto.ReplyRequestDto;
import com.sparta.cafereview.responsedto.ReplyResponseDto;
import com.sparta.cafereview.security.UserDetailsImpl;
import com.sparta.cafereview.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ReplyController {

    private final ReplyService replyService;


    //댓글 작성
    @PostMapping("/reply/{cafeid}")
    public boolean saveReply(@PathVariable Long cafeid,
                             @RequestBody ReplyRequestDto replyRequestDto,
                             @AuthenticationPrincipal UserDetailsImpl userDetails) {
        String userid = userDetails.getUsername();
        String nickname = userDetails.getNickname();

        replyRequestDto.setUserid(userid);
        replyRequestDto.setNickname(nickname);
        replyRequestDto.setCafeid(cafeid);

        return replyService.saveReply(replyRequestDto);
    }

    //댓글 전체 조회
    @GetMapping("/reply/list/{cafeid}")
    private List<ReplyResponseDto> getListReply(@PathVariable Long cafeid) {
        return replyService.getListReply(cafeid);
    }

    //댓글 전체 조회(페이징 적용)
    @GetMapping("/reply/list/paging/{cafeid}")
    private Page<Reply> getListPagingReply(@RequestParam("page") int page,
                                            @RequestParam("size") int size,
                                            @RequestParam("sortBy") String sortBy,
                                            @PathVariable Long cafeid) {
        page = page -1;
        return replyService.getListPagingReply(page, size, sortBy, cafeid);
    }

    //댓글 수정
    @PatchMapping("/reply/{cafeid}/{replyid}")
    public boolean updateReply(@PathVariable Long cafeid,
                               @PathVariable Long replyid,
                               @RequestBody ReplyRequestDto replyRequestDto,
                               @AuthenticationPrincipal UserDetailsImpl userDetails) {
        String userid = userDetails.getUsername();
        replyRequestDto.setUserid(userid);
        return replyService.update(cafeid, replyid, replyRequestDto);
    }

    //댓글 삭제
    @DeleteMapping("/reply/{cafeid}/{replyid}")
    public boolean deleteReply(@PathVariable Long cafeid,
                               @PathVariable Long replyid,
                               @AuthenticationPrincipal UserDetailsImpl userDetails) {
        String userid = userDetails.getUsername();
        return replyService.deleteReply(cafeid, replyid, userid);
    }
}
