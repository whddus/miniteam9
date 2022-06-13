package com.sparta.cafereview.controller;

import com.sparta.cafereview.requestdto.UserRequestDto;
import com.sparta.cafereview.responsedto.JwtResponseDto;
import com.sparta.cafereview.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    //회원가입
    @PostMapping("/signup")
    public boolean signupUser(@RequestBody UserRequestDto singUpData){
        return userService.signupUser(singUpData);
    }

    //로그인
    @PostMapping("/login")
    public JwtResponseDto loginUser(@RequestBody UserRequestDto loginData){
        return userService.loginUser(loginData);
    }

    //아이디 중복 검사
    @PostMapping("/signup/useridCheck")
    public boolean signupUseridCheck(@RequestBody UserRequestDto signupUseridCheckData){
        return userService.signupUseridCheck(signupUseridCheckData);
    }

}
