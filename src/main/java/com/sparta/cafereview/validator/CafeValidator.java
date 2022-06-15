package com.sparta.cafereview.validator;

import com.sparta.cafereview.requestdto.CafeRequestDto;

import com.sparta.cafereview.requestdto.CafeUpdateDto;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class CafeValidator {

    public static void validateCafeReviewInput(CafeRequestDto cafeRequestDto) {

        if (cafeRequestDto.getCafename() == null) {
            throw new IllegalArgumentException("카페이름을 입력해 주세요");
        }
        if (cafeRequestDto.getCafename().equals("")) {
            throw new IllegalArgumentException("카페이름을 입력해 주세요");
        }

        if (cafeRequestDto.getCoffeebeanname() == null) {
            throw new IllegalArgumentException("원두이름을 입력해 주세요");
        }
        if (cafeRequestDto.getCoffeebeanname().equals("")) {
            throw new IllegalArgumentException("원두이름을 입력해 주세요");
        }

        if (cafeRequestDto.getCafereview() == null) {
            throw new IllegalArgumentException("리뷰를 입력해 주세요");
        }
        if (cafeRequestDto.getCafereview().equals("")) {
            throw new IllegalArgumentException("리뷰를 입력해 주세요");
        }
        if(cafeRequestDto.getCafereview().length() > 1000){
            throw new IllegalArgumentException("리뷰 내용은 1000자를 넘길 수 없습니다");
        }
    }

    public static void validateCafeReviewImageInput(MultipartFile imgfile){
        if (imgfile == null) {
            throw new IllegalArgumentException("이미지를 등록해주세요");
        }
    }

    public static void validateUpdateCafeReviewInput(CafeUpdateDto cafeUpdateDto){
        if (cafeUpdateDto.getCafename() == null) {
            throw new IllegalArgumentException("카페이름을 입력해 주세요");
        }
        if (cafeUpdateDto.getCafename().equals("")) {
            throw new IllegalArgumentException("카페이름을 입력해 주세요");
        }

        if (cafeUpdateDto.getCoffeebeanname() == null) {
            throw new IllegalArgumentException("원두이름을 입력해 주세요");
        }
        if (cafeUpdateDto.getCoffeebeanname().equals("")) {
            throw new IllegalArgumentException("원두이름을 입력해 주세요");
        }

        if (cafeUpdateDto.getCafereview() == null) {
            throw new IllegalArgumentException("리뷰를 입력해 주세요");
        }
        if (cafeUpdateDto.getCafereview().equals("")) {
            throw new IllegalArgumentException("리뷰를 입력해 주세요");
        }
        if(cafeUpdateDto.getCafereview().length() > 1000){
            throw new IllegalArgumentException("리뷰 내용은 1000자를 넘길 수 없습니다");
        }
    }

}
