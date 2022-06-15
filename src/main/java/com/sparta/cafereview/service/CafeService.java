package com.sparta.cafereview.service;

import com.sparta.cafereview.model.Cafe;
import com.sparta.cafereview.model.Reply;
import com.sparta.cafereview.repository.CafeRepository;
import com.sparta.cafereview.repository.ReplyRepository;
import com.sparta.cafereview.requestdto.CafeRequestDto;
import com.sparta.cafereview.requestdto.CafeUpdateDto;
import com.sparta.cafereview.responsedto.CafeDetailReplyResponseDto;
import com.sparta.cafereview.responsedto.CafeDetailResponseDto;
import com.sparta.cafereview.responsedto.CafeResponseDto;
import com.sparta.cafereview.security.UserDetailsImpl;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CafeService {
    private final CafeRepository cafeRepository;
    private final ReplyRepository replyRepository;
    private final S3Service s3Service;


    //카페리뷰 저장
    @Transactional
    public boolean saveCafe(CafeRequestDto cafeRequestDto, MultipartFile imgfile, UserDetailsImpl userDetails) {
        String userid = userDetails.getUsername();
        String nickname = userDetails.getNickname();
        cafeRequestDto.setUserid(userid);
        cafeRequestDto.setNickname(nickname);
        String imgPath = s3Service.upload(imgfile);
        cafeRequestDto.setImgUrl(imgPath);
        if(userid!=null||!userid.equals("")){
        Cafe beforeSaveCafe = new Cafe(cafeRequestDto);
        cafeRepository.save(beforeSaveCafe);
        return true;
        }
        return false;
    }

    //카페리뷰 전체조회
    public List<CafeResponseDto> getCafeList() {
        return cafeRepository.findAllByOrderByIdDesc();
    }

    //카페리뷰 수정
    @Transactional
    public boolean updateCafe(Long cafeid,
                          CafeUpdateDto cafeRequestDto,
                          MultipartFile imgfile,
                          UserDetailsImpl userDetails) {
        String userid = userDetails.getUsername();
        String imgPath = s3Service.upload(imgfile, cafeRequestDto.getImgUrl());

        cafeRequestDto.setImgUrl(imgPath);
        Cafe cafe = cafeRepository.findById(cafeid).orElseThrow(
                () -> new IllegalArgumentException("카페가 존재하지 않습니다.")
        );
        if (cafe.getUserid().equals(userid)) {
            cafe.update(cafeRequestDto);
            return true;
        }
        return false;
    }

    //카페리뷰 커피빈별 검색
    public List<CafeResponseDto> getContentsSortByCoffeebeanname(String coffeebeanname) {
        return cafeRepository.findAllByCoffeebeannameOrderByIdDesc(coffeebeanname);
    }

    //카페리뷰 삭제
    public boolean deleteCafe(Long cafeid, String userid) {
        Cafe cafe = cafeRepository.findById(cafeid).orElseThrow(
                () -> new IllegalArgumentException("카페가 존재하지 않습니다.")
        );
        if (cafe.getUserid().equals(userid)) {
            cafeRepository.deleteById(cafeid);

            return true;
        }
        return false;
    }

    //카페리뷰 상세조회
    @Transactional
    public CafeDetailResponseDto getCafe(Long cafeid) {

        //등록카페조회
        Cafe cafe = cafeRepository.findById(cafeid).orElseThrow(
                () -> new NullPointerException("카페리뷰가 존재하지 않습니다."));

        //List<CafeDetailReplyResponseDto> reply = replyRepository.findAllByCafeid(cafeid);
        List<Reply> reply = replyRepository.findAllByCafeid(cafeid);
        List<CafeDetailReplyResponseDto> detail = reply.stream()
                                                            .map(CafeDetailReplyResponseDto::new)
                                                            .collect(Collectors.toList());

        return new CafeDetailResponseDto(cafe.getCoffeebeanname(), cafe.getCafename(), cafe.getImgUrl(),
                cafe.getCafereview(),cafe.getLikecafenumber(), detail
        );
    }

}
