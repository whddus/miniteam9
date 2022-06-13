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
import com.sparta.cafereview.responsedto.ReplyResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class CafeService {
    private final CafeRepository cafeRepository;

    private final ReplyRepository replyRepository;


    //저장
    @Transactional
    public boolean saveCafe(CafeRequestDto requestDto) {
        //저장 유무를 판단 (영속성 컨텍스트 확인)
        Cafe beforeSaveCafe = new Cafe(requestDto);
        Cafe SaveCafe = cafeRepository.save(beforeSaveCafe);
        return true;
    }

    //전체조회
    public List<CafeResponseDto> getCafeList() {
        List<CafeResponseDto> cafeList = cafeRepository.findAllByOrderByIdDesc();
        return cafeList;
    }

    //수정
    @Transactional
    public Boolean update(Long cafeid, CafeUpdateDto requestDto, String userid) {
        Cafe cafe = cafeRepository.findById(cafeid).orElseThrow(
                () -> new IllegalArgumentException("카페가 존재하지 않습니다.")
        );
        if (cafe.getUserid().equals(userid)) {
            cafe.update(requestDto);
            return true;
        }
        return false;
    }

    //검색
    public List<CafeResponseDto> sortByCoffeebeanname(String coffeebeanname) {
        List<CafeResponseDto> cafe = cafeRepository.findAllByCoffeebeannameOrderByIdDesc(coffeebeanname);
        return cafe;
    }

    //삭제
    public Boolean deleteCafe(Long cafeid, String userid) {
        Cafe cafe = cafeRepository.findById(cafeid).orElseThrow(
                () -> new IllegalArgumentException("카페가 존재하지 않습니다.")
        );
        if (cafe.getUserid().equals(userid)) {
            cafeRepository.deleteById(cafeid);

            return true;
        }
        return false;
    }

    //상세조회
    @Transactional
    public CafeDetailResponseDto getCafe(Long cafeid) {

        //등록카페조회
        Cafe cafe = cafeRepository.findById(cafeid).orElseThrow(
                () -> new NullPointerException("카페리뷰가 존재하지 않습니다."));
        String cafename = cafe.getCafename();

        //List<CafeDetailReplyResponseDto> reply = replyRepository.findAllByCafeid(cafeid);
        List<Reply> reply = replyRepository.findAllByCafeId(cafeid);
        List<CafeDetailReplyResponseDto> detail = reply.stream().map(CafeDetailReplyResponseDto::new).collect(Collectors.toList());

        return new CafeDetailResponseDto(cafe.getCoffeebeanname(), cafe.getCafename(), cafe.getImgUrl(),
                cafe.getCafereview(), detail
        );
    }

}
