package com.sparta.cafereview.controller;

import com.sparta.cafereview.requestdto.CafeRequestDto;
import com.sparta.cafereview.requestdto.CafeUpdateDto;
import com.sparta.cafereview.responsedto.CafeDetailResponseDto;
import com.sparta.cafereview.responsedto.CafeResponseDto;
import com.sparta.cafereview.security.UserDetailsImpl;
import com.sparta.cafereview.service.CafeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CafeController {


    private final CafeService cafeService;

    //저장
    @PostMapping("/cafe/save")
    public Boolean saveCafe(@RequestBody CafeRequestDto cafeRequestDto,
                            //받는 데이터 방식도 변경 및 추가
                            //ex @RequestPart MultipartFile file,
                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        String userid = userDetails.getUsername();
        cafeRequestDto.setUserid(userid);
        //이곳에 사진을 먼저 저장하는 함수 필요
        //ex) String imageUrl = AWSS3서비스이름.함수이름(file);
        //ex) cafeRequestDto.setImageUrl(imageUrl);
        boolean cafe = cafeService.saveCafe(cafeRequestDto);
        return cafe;
    }

    //전체조회
    @GetMapping("/cafe/list")
    public List<CafeResponseDto> getCafeList() {
        return cafeService.getCafeList();
    }

    //수정
    @PatchMapping("cafe/{cafeid}/update")
    public boolean updateCafe(@PathVariable Long cafeid, @RequestBody CafeUpdateDto requestDto,@AuthenticationPrincipal UserDetailsImpl userDetails) {
        String userid = userDetails.getUsername();
        boolean result = cafeService.update(cafeid, requestDto, userid);
        return result;
    }

    //원두별로 카페리뷰 검색
    @PostMapping("/cafe/{coffeebeanname}/search")
    public List<CafeResponseDto> getContents(@PathVariable String coffeebeanname) {
        List<CafeResponseDto> cafe = cafeService.sortByCoffeebeanname(coffeebeanname);
        return cafe;
    }

    //삭제
    @DeleteMapping("/cafe/{cafeid}/delete")
    public Boolean deleteCafe(@PathVariable Long cafeid,@AuthenticationPrincipal UserDetailsImpl userDetails) {
        String userid = userDetails.getUsername();
        Boolean result = cafeService.deleteCafe(cafeid, userid);
        return result;
    }

    //상세조회
    @GetMapping("/cafe/{cafeid}")
    public CafeDetailResponseDto getCafe(@PathVariable Long cafeid) {
        return cafeService.getCafe(cafeid);
    }

}
