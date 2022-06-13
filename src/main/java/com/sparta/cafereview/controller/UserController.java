package com.sparta.cafereview.controller;

import com.sparta.cafereview.requestdto.UserRequestDto;
import com.sparta.cafereview.responsedto.JwtResponseDto;
import com.sparta.cafereview.security.UserDetailsImpl;
import com.sparta.cafereview.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    //로그인 중복 확인
    @GetMapping("/login/check")
    public boolean loginUserCheck(@AuthenticationPrincipal UserDetailsImpl userDetails){
//        Object authentication = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userService.loginUserCheck(userDetails);
    }
}
