package com.example.mockito_test.spring;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    public static final String EXCEPTION_MSG = "존재 하는 ID 입니다.";
    @Autowired private MockMvc mvc;
    @MockBean private UserService userService;

    @DisplayName("회원가입 요청 - 성공")
    @Test
    void givenUserInfo_whenSignup_thenReturns200() throws Exception {
        // Given
        User user = createUser();
        BDDMockito.given(userService.signup(any(User.class))).willReturn(user);

        // When & Then
        mvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonEncode(user))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.userId").value(user.getUserId()))
                .andExpect(jsonPath("$.message").isEmpty());
        BDDMockito.then(userService).should().signup(any(User.class));
    }

    @DisplayName("회원가입 요청 - 실패")
    @Test
    void givenUserInfo_whenSignup_thenReturns500() throws Exception {
        // Given
        User user = createUser();
        BDDMockito.given(userService.signup(any(User.class))).willThrow(new RuntimeException(EXCEPTION_MSG));

        // When & Then
        mvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonEncode(user))
                )
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.code").value(500))
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.message").value(EXCEPTION_MSG));
        BDDMockito.then(userService).should().signup(any(User.class));
    }

    @DisplayName("회원 정보 조회 요청")
    @Test
    void givenUserId_whenGetUser_thenReturns200() throws Exception {
        // Given
        User user = createUser();
        BDDMockito.given(userService.searchUser(anyString())).willReturn(user);

        // When & Then
        mvc.perform(get("/users/" + user.getUserId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.userId").value(user.getUserId()))
                .andExpect(jsonPath("$.message").isEmpty());
        BDDMockito.then(userService).should().searchUser(anyString());
    }

    private User createUser() {
        return new User(
                "test1",
                "홍길동",
                27,
                true
        );
    }

    private String jsonEncode(Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }

}