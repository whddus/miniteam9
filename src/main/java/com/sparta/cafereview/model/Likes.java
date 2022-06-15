package com.sparta.cafereview.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Likes {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column(nullable = false)
    private Long cafeid;
    @Column(nullable = false)
    private String userid;

    public Likes(Long cafeid, String userid){
        this.cafeid = cafeid;
        this.userid = userid;
    }
}
