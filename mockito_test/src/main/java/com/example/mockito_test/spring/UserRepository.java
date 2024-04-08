package com.example.mockito_test.spring;

import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;

@Repository
public class UserRepository {

    private static final ConcurrentHashMap<String, User> USER_STORE = new ConcurrentHashMap<>();

    public User findById(String userId) {
        return USER_STORE.get(userId);
    }

    public User save(User user) {
        User storedUser = USER_STORE.get(user.getUserId());

        if(storedUser != null)
            throw new RuntimeException("존재 하는 ID 입니다.");

        USER_STORE.put(user.getUserId(), user);

        return user;
    }
}
