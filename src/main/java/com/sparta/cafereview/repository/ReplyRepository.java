package com.sparta.cafereview.repository;

import com.sparta.cafereview.model.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findAllByCafeid(Long cafeid);
}
