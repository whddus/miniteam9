package com.sparta.cafereview.model;

import com.sparta.cafereview.requestdto.CafeRequestDto;
import com.sparta.cafereview.requestdto.CafeUpdateDto;
import com.sparta.cafereview.validator.CafeValidator;
import com.sparta.cafereview.validator.ReplyValidator;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter // get 함수를 일괄적으로 만들어줍니다.
@NoArgsConstructor // 기본 생성자를 만들어줍니다.
@Entity // DB 테이블 역할을 합니다.
public class Cafe {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column(nullable = false)
    private String cafename;
    @Column(nullable = false)
    private String coffeebeanname;
    @Column(nullable = false)
    private String imgUrl;
    @Column(nullable = false)
    private String cafereview;
    @Column(nullable = false)
    private String userid;
    @Column(nullable = false)
    private String nickname;
    @Column(nullable = false)
    private int likecafenumber = 0;

    public Cafe(CafeRequestDto requestDto){

        CafeValidator.validateCafeReviewInput(requestDto);

        this.cafename = requestDto.getCafename();
        this.coffeebeanname = requestDto.getCoffeebeanname();
        this.imgUrl = requestDto.getImgUrl();
        this.cafereview = requestDto.getCafereview();
        this.userid = requestDto.getUserid();
        this.nickname = requestDto.getNickname();
    }

    public void update(CafeUpdateDto requestDto) {

        CafeValidator.validateUpdateCafeReviewInput(requestDto);

        this.cafename = requestDto.getCafename();
        this.coffeebeanname = requestDto.getCoffeebeanname();
        this.imgUrl = requestDto.getImgUrl();
        this.cafereview = requestDto.getCafereview();
    }

    public void likecafe(){
        this.likecafenumber++;
    }

    public void unlikecafe(){
        this.likecafenumber--;
    }
}
