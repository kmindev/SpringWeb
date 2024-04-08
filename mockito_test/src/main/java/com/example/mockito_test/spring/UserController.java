package com.example.mockito_test.spring;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UserController {

    private final UserService userService;

    @GetMapping("{userId}")
    public ResponseEntity<ResponseDto<User>> getUser(@PathVariable("userId") String userId) {
        return ResponseEntity.ok(
                ResponseDto.okWithData(userService.searchUser(userId))
        );
    }

    @PostMapping
    public ResponseEntity<ResponseDto<User>> saveUser(@RequestBody User user) {
        return ResponseEntity.ok(
                ResponseDto.okWithData(userService.signup(user))
        );
    }

}
