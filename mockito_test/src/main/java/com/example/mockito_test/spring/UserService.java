package com.example.mockito_test.spring;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User searchUser(String userId) {
        return userRepository.findById(userId);
    }

    public User signup(User user) {
        return userRepository.save(user);
    }
}
