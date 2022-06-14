package com.sparta.cafereview.controller;

import com.sparta.cafereview.requestdto.CafeRequestDto;
import com.sparta.cafereview.requestdto.CafeUpdateDto;
import com.sparta.cafereview.responsedto.CafeDetailResponseDto;
import com.sparta.cafereview.responsedto.CafeResponseDto;
import com.sparta.cafereview.security.UserDetailsImpl;
import com.sparta.cafereview.service.CafeService;
import com.sparta.cafereview.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CafeController {
    private final CafeService cafeService;
    private final S3Service s3Service;

    //저장
    @PostMapping("/cafereview")
    public Boolean saveCafe(@RequestPart("post-data") CafeRequestDto cafeRequestDto,
                            @RequestPart("img") MultipartFile file,
                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return cafeService.saveCafe(cafeRequestDto,file,userDetails);
    }


    //전체 조회
    @GetMapping("/cafereview/list")
    public List<CafeResponseDto> getCafeList() {
        return cafeService.getCafeList();
    }

    //수정
    @PatchMapping("/cafereview/{cafeid}")
    public boolean updateCafe(@PathVariable Long cafeid,
                              @RequestPart("post-data") CafeUpdateDto cafeRequestDto,
                              @RequestPart("img") MultipartFile file,
                              @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return cafeService.update(cafeid, cafeRequestDto,file,userDetails);
    }

    //카페리뷰 카테고리별 조회
    @PostMapping("/cafereview/list/{coffeebeanname}")
    public List<CafeResponseDto> getContents(@PathVariable String coffeebeanname) {
        return cafeService.sortByCoffeebeanname(coffeebeanname);
    }

    //삭제
    @DeleteMapping("/cafereview/{cafeid}")
    public boolean deleteCafe(@PathVariable Long cafeid, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        String userid = userDetails.getUsername();
        return cafeService.deleteCafe(cafeid, userid);
    }

    //상세조회
    @GetMapping("/cafereview/list/{cafeid}")
    public CafeDetailResponseDto getCafe(@PathVariable Long cafeid) {
        return cafeService.getCafe(cafeid);
    }
}
