package com.sparta.cafereview.repository;

import com.sparta.cafereview.model.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Likes, Long> {
    Optional<Likes> findByUseridAndCafeid(String userid, Long cafeid);
}
