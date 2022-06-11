package com.sparta.cafereview.service;
import com.sparta.cafereview.model.Cafe;
import com.sparta.cafereview.repository.CafeRepository;
import com.sparta.cafereview.requestdto.CafeRequestDto;
import com.sparta.cafereview.requestdto.CafeUpdateDto;
import com.sparta.cafereview.responsedto.CafeDetailReplyResponseDto;
import com.sparta.cafereview.responsedto.CafeDetailResponseDto;
import com.sparta.cafereview.responsedto.CafeResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;



@RequiredArgsConstructor
@Service
public class CafeService {
    private final CafeRepository cafeRepository;

    //저장
    @Transactional
    public boolean saveCafe(CafeRequestDto requestDto,String userid) {
        if (userid != null) {
            Cafe cafe = new Cafe(requestDto,userid);
        cafeRepository.save(cafe);
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
    public Boolean update(Long cafeid, CafeUpdateDto requestDto,String userid) {
        Cafe cafe = cafeRepository.findById(cafeid).orElseThrow(
                () -> new IllegalArgumentException("카페가 존재하지 않습니다.")
        );
        if(cafe.getUserid().equals(userid)){
            cafe.update(requestDto);
            return true;
        }
        return false;
    }
    //검색
    public List<CafeResponseDto> sortByCoffeebeanname(String coffeebeanname) {
        List<CafeResponseDto> cafe = cafeRepository.findAllByCoffeebeannameOrderByIdDesc(coffeebeanname );
        return cafe;
    }
    //삭제
    public Boolean deleteCafe(Long cafeid,String userid) {
        Cafe cafe = cafeRepository.findById(cafeid).orElseThrow(
                () -> new IllegalArgumentException("카페가 존재하지 않습니다.")
        );
        if(cafe.getUserid().equals(userid)){
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

        List<CafeDetailReplyResponseDto> reply = replyRepository.findAllBycafeid(cafeid);

        return new CafeDetailResponseDto(cafe.getCoffeebeanname(),cafe.getCafename(),cafe.getImageUrl(),
                cafe.getCafereview(), reply
                );
    }

}
