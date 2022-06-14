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
        String userid = userDetails.getUsername();
        cafeRequestDto.setUserid(userid);

        //이미지 경로를 받아온다.
        String imgPath = s3Service.upload(file);

        //Dto에 담아준뒤 , 서비스 로직에 넘긴다.
        cafeRequestDto.setImgUrl(imgPath);
        boolean cafe = cafeService.saveCafe(cafeRequestDto);
        return cafe;
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
        String userid = userDetails.getUsername();
        String imgPath = s3Service.upload(file, cafeRequestDto.getImgUrl());

        cafeRequestDto.setImgUrl(imgPath);

        boolean result = cafeService.update(cafeid, cafeRequestDto, userid);
        return result;
    }

    //카페리뷰 카테고리별 조회
    @PostMapping("/cafereview/list/{coffeebeanname}")
    public List<CafeResponseDto> getContents(@PathVariable String coffeebeanname) {
        List<CafeResponseDto> cafe = cafeService.sortByCoffeebeanname(coffeebeanname);
        return cafe;
    }

    //삭제
    @DeleteMapping("/cafereview/{cafeid}")
    public Boolean deleteCafe(@PathVariable Long cafeid,@AuthenticationPrincipal UserDetailsImpl userDetails) {
        String userid = userDetails.getUsername();
        Boolean result = cafeService.deleteCafe(cafeid, userid);
        return result;
    }

    //상세조회
    @GetMapping("/cafereview/list/{cafeid}")
    public CafeDetailResponseDto getCafe(@PathVariable Long cafeid) {
        return cafeService.getCafe(cafeid);
    }
}
