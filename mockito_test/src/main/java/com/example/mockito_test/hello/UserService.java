package com.example.mockito_test.hello;

public class UserService {

    public User getUser() {
        return new User("realUser", 27, true);
    }
}
