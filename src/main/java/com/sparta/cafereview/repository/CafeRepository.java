package com.sparta.cafereview.repository;

import com.sparta.cafereview.model.Cafe;
import com.sparta.cafereview.responsedto.CafeResponseDto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CafeRepository extends JpaRepository<Cafe, Long> {
    List<CafeResponseDto> findAllByOrderByIdDesc();
    List<CafeResponseDto> findAllByCoffeebeannameOrderByIdDesc(String coffeebeanname);
//    Page<CafeResponseDto> findAllByPageOrderByIdDesc(Pageable pageable);
}
