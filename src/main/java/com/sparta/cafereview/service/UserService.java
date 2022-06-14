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
    public boolean signupUser(UserRequestDto singUpData){
        if(singUpData.getUserid() == null){
            throw new IllegalArgumentException("아이디를 입력해 주세요");
        } else if (singUpData.getUserid().equals("")) {
            throw new IllegalArgumentException("아이디를 입력해 주세요");
        } else if (!singUpData.getUserid().matches("^[a-zA-Z\\d]+@[a-zA-Z\\d]+.[a-z]+$")) {
            throw new IllegalArgumentException("아이디 형식을 확인해 주세요");
        }

        if(singUpData.getNickname() == null){
            throw new IllegalArgumentException("닉네임을 입력해 주세요");
        } else if (singUpData.getNickname().equals("")) {
            throw new IllegalArgumentException("닉네임을 입력해 주세요");
        } else if (!singUpData.getNickname().matches("^[가-힣a-zA-Z]+$")) {
            throw new IllegalArgumentException("닉네임 형식을 확인해 주세요");
        }

        if(singUpData.getPassword() == null){
            throw new IllegalArgumentException("비밀번호를 입력해 주세요");
        } else if (singUpData.getPassword().equals("")) {
            throw new IllegalArgumentException("비밀번호를 입력해 주세요");
        } else if (!singUpData.getPassword().matches("^((?=.*[A-Za-z])|(?=.*\\d)|(?=.*[~!@#$%^&*()_+=])$)[A-Za-z\\d~!@#$%^&*()_+=]{10,25}$")) {
            throw new IllegalArgumentException("비밀번호 형식을 확인해 주세요");
        } else if (singUpData.getPassword().matches("(\\w)\\1\\1")) {
            throw new IllegalArgumentException("비밀번호는 동일한 문자가 3개이상 연속 될 수 없습니다");
        }

        User beforeSaveUser = new User(singUpData);
        beforeSaveUser.encryptPassword(passwordEncoder);
        User saveUser = userRepository.save(beforeSaveUser);
        User checkUser = userRepository.findById(saveUser.getId()).orElse(null);
        return saveUser.equals(checkUser);
    }

    //로그인
    public JwtResponseDto loginUser(UserRequestDto loginData){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginData.getUserid(), loginData.getPassword()));
        return createJwtToken(authentication);
    }

    //아이디 중복 검사
    public boolean signupUseridCheck(UserRequestDto signupUseridCheckData){
        Optional<User> found = userRepository.findByUserid(signupUseridCheckData.getUserid());
        return !found.isPresent();
    }

    //JWT 토큰 생성기
    private JwtResponseDto createJwtToken(Authentication authentication){
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        String token = jwtTokenProvider.generateToken(principal);
        String nickname = ((UserDetailsImpl) authentication.getPrincipal()).getNickname();
        return new JwtResponseDto(token, nickname);
    }
}
