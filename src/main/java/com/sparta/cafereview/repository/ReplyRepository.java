package com.sparta.cafereview.repository;

import com.sparta.cafereview.model.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findAllByCafeId(Long cafeId);
    Optional<Reply> findById(Long id);
}
