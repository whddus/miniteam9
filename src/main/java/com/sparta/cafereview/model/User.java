package com.sparta.cafereview.model;

import com.sparta.cafereview.requestdto.UserRequestDto;
import com.sparta.cafereview.validator.UserValidator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column(nullable = false, unique = true)
    private String userid;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String nickname;
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    public User(UserRequestDto params, UserRoleEnum role){

        UserValidator.validateUserInput(params);

        this.userid = params.getUserid();
        this.password = params.getPassword();
        this.nickname = params.getNickname();
        this.role = role;
    }

    public void encryptPassword(PasswordEncoder passwordEncoder) {
        password = passwordEncoder.encode(password);
    }
}
