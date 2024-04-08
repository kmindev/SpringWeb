package com.example.mockito_test.hello;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
public class User {
    private String name;
    private int age;
    private boolean active;
}
