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


    //저장
    @Transactional
    public boolean saveCafe(CafeRequestDto cafeRequestDto, MultipartFile file, UserDetailsImpl userDetails) {
        String userid = userDetails.getUsername();
        cafeRequestDto.setUserid(userid);

        //이미지 경로를 받아온다.
        String imgPath = s3Service.upload(file);

        //Dto에 담아준뒤 , 서비스 로직에 넘긴다.
        cafeRequestDto.setImgUrl(imgPath);

        //userid 있는지 판단
        if(userid!=null||!userid.equals("")){
        //저장 유무를 판단 (영속성 컨텍스트 확인)
        Cafe beforeSaveCafe = new Cafe(cafeRequestDto);
        cafeRepository.save(beforeSaveCafe);
        return true;
        }
        return false;
    }

    //전체조회
    public List<CafeResponseDto> getCafeList() {
        List<CafeResponseDto> cafeList = cafeRepository.findAllByOrderByIdDesc();
        return cafeList;
    }

    //수정
    @Transactional
    public Boolean update(Long cafeid,
                          CafeUpdateDto cafeRequestDto,
                          MultipartFile file,
                          UserDetailsImpl userDetails) {
        String userid = userDetails.getUsername();
        String imgPath = s3Service.upload(file, cafeRequestDto.getImgUrl());

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
        List<Reply> reply = replyRepository.findAllByCafeid(cafeid);
        List<CafeDetailReplyResponseDto> detail = reply.stream().map(CafeDetailReplyResponseDto::new).collect(Collectors.toList());

        return new CafeDetailResponseDto(cafe.getCoffeebeanname(), cafe.getCafename(), cafe.getImgUrl(),
                cafe.getCafereview(), detail
        );
    }

}
