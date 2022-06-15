package com.sparta.cafereview.validator;

import com.sparta.cafereview.requestdto.UserRequestDto;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {

    public static void validateUserInput(UserRequestDto singUpData){
        //ID 검사
        if(singUpData.getUserid() == null){
            throw new IllegalArgumentException("아이디를 입력해 주세요");
        }
        if (singUpData.getUserid().equals("")) {
            throw new IllegalArgumentException("아이디를 입력해 주세요");
        }
        if (!singUpData.getUserid().matches("^[a-zA-Z\\d]+@[a-zA-Z\\d]+.[a-z]+$")) {
            throw new IllegalArgumentException("아이디 형식을 확인해 주세요");
        }

        //닉네임 검사
        if(singUpData.getNickname() == null){
            throw new IllegalArgumentException("닉네임을 입력해 주세요");
        }
        if (singUpData.getNickname().equals("")) {
            throw new IllegalArgumentException("닉네임을 입력해 주세요");
        }
        if (!singUpData.getNickname().matches("^[가-힣a-zA-Z]+$")) {
            throw new IllegalArgumentException("닉네임 형식을 확인해 주세요");
        }

        //비밀번호 검사
        if(singUpData.getPassword() == null){
            throw new IllegalArgumentException("비밀번호를 입력해 주세요");
        }
        if (singUpData.getPassword().equals("")) {
            throw new IllegalArgumentException("비밀번호를 입력해 주세요");
        }
        if (!singUpData.getPassword().matches("^((?=.*[A-Za-z])|(?=.*\\d)|(?=.*[~!@#$%^&*()_+=])$)[A-Za-z\\d~!@#$%^&*()_+=]{10,25}$")) {
            throw new IllegalArgumentException("비밀번호 형식을 확인해 주세요");
        }
        if (singUpData.getPassword().matches("(\\w)\\1\\1")) {
            throw new IllegalArgumentException("비밀번호는 동일한 문자가 3개이상 연속 될 수 없습니다");
        }
    }
}
