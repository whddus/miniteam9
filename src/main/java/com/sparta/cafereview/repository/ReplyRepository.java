package com.sparta.cafereview.repository;

import com.sparta.cafereview.model.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    Reply findByCafeidAndId(Long cafeid, Long id);
    List<Reply> findAllByCafeid(Long cafeid);
    Page<Reply> findAllByCafeidPage(Long cafeid, Pageable pageable);
}
