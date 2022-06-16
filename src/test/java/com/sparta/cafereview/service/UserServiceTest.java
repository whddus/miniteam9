package com.sparta.cafereview.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.cafereview.model.UserRoleEnum;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private HttpHeaders headers;
    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    @Test
    public void setup() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @Nested
    @DisplayName("회원가입, 로그인, 회원가입시 ID 중복 확인 - 정상 데이터")
    class RegisterUser {
        @Test
        @DisplayName("회원가입")
        void signupUser() throws JsonProcessingException {
            //given
            UserDto userRequest = UserDto.builder()
                    .id(null)
                    .userid("beomsoo@dev.com")
                    .password("1q2w3e4r5t")
                    .nickname("beomsoo")
                    .role(UserRoleEnum.USER)
                    .build();

            String requestBoby = mapper.writeValueAsString(userRequest);
            HttpEntity<String> request = new HttpEntity<>(requestBoby, headers);

            //when
            ResponseEntity<Boolean> response = restTemplate.postForEntity(
                    "/user/signup",
                    request,
                    Boolean.class);

            //then
            assertEquals(HttpStatus.OK, response.getStatusCode());
        }

        @Nested
        @DisplayName("회원가입 - ID")
        class IdFail {
            @Test
            @DisplayName("null")
            void signupUserTest1() throws JsonProcessingException {
                //given
                UserDto userRequest = UserDto.builder()
                        .id(null)
                        .userid(null)
                        .password("1q2w3e4r5t")
                        .nickname("beomsoo")
                        .role(UserRoleEnum.USER)
                        .build();

                String requestBoby = mapper.writeValueAsString(userRequest);
                HttpEntity<String> request = new HttpEntity<>(requestBoby, headers);

                //when
                ResponseEntity<UserDto> response = restTemplate.postForEntity(
                        "/user/signup",
                        request,
                        UserDto.class);

                //then
                assertTrue(response.getStatusCode() == HttpStatus.BAD_REQUEST
                        || response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR);
            }

            @Test
            @DisplayName("빈 문자열")
            void signupUserTest2() throws JsonProcessingException {
                //given
                UserDto userRequest = UserDto.builder()
                        .id(null)
                        .userid("")
                        .password("1q2w3e4r5t")
                        .nickname("beomsoo")
                        .role(UserRoleEnum.USER)
                        .build();

                String requestBoby = mapper.writeValueAsString(userRequest);
                HttpEntity<String> request = new HttpEntity<>(requestBoby, headers);

                //when
                ResponseEntity<UserDto> response = restTemplate.postForEntity(
                        "/user/signup",
                        request,
                        UserDto.class);

                //then
                assertTrue(response.getStatusCode() == HttpStatus.BAD_REQUEST
                        || response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR);
            }

            @Test
            @DisplayName("형식오류")
            void signupUserTest3() throws JsonProcessingException {
                //given
                UserDto userRequest = UserDto.builder()
                        .id(null)
                        .userid("Moonbeomsoo")
                        .password("1q2w3e4r5t")
                        .nickname("beomsoo")
                        .role(UserRoleEnum.USER)
                        .build();

                String requestBoby = mapper.writeValueAsString(userRequest);
                HttpEntity<String> request = new HttpEntity<>(requestBoby, headers);

                //when
                ResponseEntity<UserDto> response = restTemplate.postForEntity(
                        "/user/signup",
                        request,
                        UserDto.class);

                //then
                assertTrue(response.getStatusCode() == HttpStatus.BAD_REQUEST
                        || response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        @Test
        @DisplayName("로그인")
        void loginUser() throws JsonProcessingException {
            //given
            UserDto userRequest = UserDto.builder()
                    .userid("beomsoo@dev.com")
                    .password("1q2w3e4r5t")
                    .build();

            String requestBoby = mapper.writeValueAsString(userRequest);
            HttpEntity<String> request = new HttpEntity<>(requestBoby, headers);

            //when
            ResponseEntity<testJwtResponseDto> response = restTemplate.postForEntity(
                    "/user/login",
                    request,
                    testJwtResponseDto.class);

            //then
            assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        }

        @Test
        @DisplayName("회원가입시 ID 중복 확인")
        void signupUseridCheck() throws JsonProcessingException {
            //given
            UserDto userRequest = UserDto.builder()
                    .userid("beomsoo@dev.com")
                    .build();

            String requestBoby = mapper.writeValueAsString(userRequest);
            HttpEntity<String> request = new HttpEntity<>(requestBoby, headers);

            //when
            ResponseEntity<Boolean> response = restTemplate.postForEntity(
                    "/user/signup/useridCheck",
                    request,
                    Boolean.class);

            //then
            assertEquals(HttpStatus.OK, response.getStatusCode());
        }
    }

    @Getter
    @Setter
    @Builder
    static class UserDto {
        private Long id;
        private String userid;
        private String password;
        private String nickname;
        private UserRoleEnum role;
    }

    @Getter
    static class testJwtResponseDto {
        private String accessToken;
    }
}