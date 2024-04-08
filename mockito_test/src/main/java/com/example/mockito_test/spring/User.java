package com.example.mockito_test.spring;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class User {
    private String userId;
    private String name;
    private int age;
    private boolean active;
}
