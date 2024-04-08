package com.example.mockito_test.hello;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

public class MockitoTest {
//    @Mock UserService userService;

    @Test
    void mockTest1() {
        UserService mockUserService = Mockito.mock(UserService.class);
        mockUserService.getUser();

        verify(mockUserService).getUser();
    }

    @Test
    void mockTest2() {
        UserService mockUserService = Mockito.mock(UserService.class);
        mockUserService.getUser();
        mockUserService.getUser();

        verify(mockUserService, times(2)).getUser();
    }

//    @Test
//    void mockTest2() {
//        UserService mockUserService = Mockito.spy(UserService.class);
//
//        assertThat(user.ge).isEqualTo(user);
//    }

}
