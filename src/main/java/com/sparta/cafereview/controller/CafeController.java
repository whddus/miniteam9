package com.sparta.cafereview.controller;
import com.sparta.cafereview.requestdto.CafeRequestDto;
import com.sparta.cafereview.requestdto.CafeUpdateDto;
import com.sparta.cafereview.responsedto.CafeDetailResponseDto;
import com.sparta.cafereview.responsedto.CafeResponseDto;
import com.sparta.cafereview.service.CafeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CafeController {


    private final CafeService cafeService;
    //저장
    @PostMapping("/cafe/save")
    public Boolean saveCafe(@RequestBody CafeRequestDto requestDto) {

        String userid = "whddus@whddus";
        boolean cafe = cafeService.saveCafe(requestDto, userid);
        return cafe;
    }

    //전체조회
    @GetMapping("/cafe/list")
    public List<CafeResponseDto> getCafeList() {
        return cafeService.getCafeList();
    }

    //수정
    @PatchMapping("cafe/{cafeid}/update")
    public boolean updateCafe(@PathVariable Long cafeid, @RequestBody CafeUpdateDto requestDto) {
        String userid = "whddus@whddus";
        boolean result = cafeService.update(cafeid, requestDto,userid);
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
    public Boolean deleteCafe(@PathVariable Long cafeid) {
        String userid = "whddus@whddus";
            Boolean result = cafeService.deleteCafe(cafeid,userid);
            return result;
    }
    //상세조회
    @GetMapping("/cafe/{cafeid}")
    public CafeDetailResponseDto getCafe(@PathVariable Long cafeid) {
        return cafeService.getCafe(cafeid);
    }

}
