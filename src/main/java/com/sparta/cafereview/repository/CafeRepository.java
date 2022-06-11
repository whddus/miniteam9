package com.sparta.cafereview.repository;

import com.sparta.cafereview.model.Cafe;
import com.sparta.cafereview.responsedto.CafeResponseDto;
import jdk.javadoc.internal.doclets.formats.html.Contents;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CafeRepository extends JpaRepository<Cafe, Long> {
    List<CafeResponseDto> findAllByOrderByIdDesc();
    List<CafeResponseDto> findAllByCoffeebeannameOrderByIdDesc(String coffeebeanname);

    Cafe findByUserid(String userid);
}
