package com.cos.jwtserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestApiController {
    public static void main(String[] args) {
        StringBuilder s = new StringBuilder("a2s");
        s.reverse();
    }

    @GetMapping("/home")
    public String home() {
        return "<h1>home</h1>";
    }

    @PostMapping("/token")
    public String token() {
        return "<h1>token<h1>";
    }
}
