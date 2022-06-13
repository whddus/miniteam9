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

    //로그인 중복 확인
    public String loginUserCheck(UserDetailsImpl userDetails){
        if(userDetails != null){
            return userDetails.getNickname();
        }
        return "";
    }

    //JWT 토큰 생성기
    private JwtResponseDto createJwtToken(Authentication authentication){
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        String token = jwtTokenProvider.generateToken(principal);
        return new JwtResponseDto(token);
    }
}
