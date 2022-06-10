package com.sparta.cafereview.service;

import com.sparta.cafereview.model.User;
import com.sparta.cafereview.repository.UserRepository;
import com.sparta.cafereview.requestdto.UserRequestDto;
import com.sparta.cafereview.responsedto.JwtResponseDto;
import com.sparta.cafereview.security.JwtTokenProvider;
import com.sparta.cafereview.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    //회원가입
    public boolean signUpuser(UserRequestDto singUpData){
        User user = new User(singUpData);
        user.encryptPassword(passwordEncoder);
        return true;
    }

    //로그인
    public JwtResponseDto loginUser(UserRequestDto loginData){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginData.getUserid(), loginData.getPassword()));
        return createJwtToken(authentication);
    }

    //로그아웃
    public String logout(Authentication authentication){

        return "로그아웃 되었습니다.";
    }

    //아이디 중복 검사
    public boolean signupUseridCheck(UserRequestDto signupUseridCheckData){
        Optional<User> found = userRepository.findByUserid(signupUseridCheckData.getUserid());
        return !found.isPresent();
    }

    //로그인 중복 확인
    public boolean loginUserCheck(Object authentication){
        return !authentication.equals("anonymousUser");
    }

    //JWT 토큰 생성기
    private JwtResponseDto createJwtToken(Authentication authentication){
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        String token = jwtTokenProvider.generateToken(principal);
        return new JwtResponseDto(token);
    }
}
