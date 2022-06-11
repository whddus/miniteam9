package com.sparta.cafereview.model;

import com.sparta.cafereview.requestdto.CafeRequestDto;
import com.sparta.cafereview.requestdto.CafeUpdateDto;
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
    private String imageUrl;
    @Column(nullable = false)
    private String cafereview;
    @Column(nullable = false)
    private String userid;

    public Cafe(CafeRequestDto requestDto,String userid){
        this.cafename = requestDto.getCafename();
        this.coffeebeanname = requestDto.getCoffeebeanname();
        this.imageUrl = requestDto.getImageUrl();
        this.cafereview = requestDto.getCafereview();
        this.userid = userid;
    }

    public void update(CafeUpdateDto requestDto) {
        this.cafename = requestDto.getCafename();
        this.coffeebeanname = requestDto.getCoffeebeanname();
        this.imageUrl = requestDto.getImageUrl();
        this.cafereview = requestDto.getCafereview();
    }

}
