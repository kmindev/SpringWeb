package com.example.mockito_test.spring;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks private UserService sut;
    @Mock private UserRepository userRepository;

    @DisplayName("user 정보를 입력 하면, user 정보를 저장 한다.")
    @Test
    void givenUserInfo_whenSignup_thenReturnsUserInfo() {
        // Given
        User user = createUser();
        BDDMockito.given(userRepository.save(user)).willReturn(user);

        // When
        sut.signup(user);

        // Then
        BDDMockito.then(userRepository).should().save(user);
    }

    @DisplayName("이미 가입된 userId 입력 하면, 예외를 발생 시킨다.")
    @Test
    void givenUserInfo_whenSignup_thenThrowsException() {
        // Given
        User user = createUser();
        BDDMockito.given(userRepository.save(user)).willThrow(RuntimeException.class);

        // When
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> sut.signup(user)
        );

        // Then
        assertThat(exception).isInstanceOf(RuntimeException.class);
        BDDMockito.then(userRepository).should().save(user);
    }

    @DisplayName("유저 id를 입력하면, userId에 해당 하는 user 정보를 반환 한다.")
    @Test
    void given_when_then() {
        // Given
        String userId = "test1";
        User user = createUser();
        BDDMockito.given(userRepository.findById(userId)).willReturn(user);

        // When
        sut.searchUser(userId);

        // Then
        BDDMockito.then(userRepository).should().findById(userId);
    }

    private User createUser() {
        return new User(
                "test1",
                "홍길동",
                27,
                true
        );
    }
}