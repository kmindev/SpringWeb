package com.cos.security1.controller;

import com.cos.security1.model.User;
import com.cos.security1.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller // view를 리턴한다.
@RequiredArgsConstructor
public class IndexController {

    private final UserService userService;

    @GetMapping({"", "/"})
    public String index() {
        // 머스테치 (템플릿엔진) 기본경로 src/main/resources/
        // view resolver 설정: templates (prefix), .mustache (suffix) 생략 가능
        return "index";
    }

    @GetMapping("/user")
    public @ResponseBody String user() {
        return "user";
    }

    @GetMapping("/admin")
    public @ResponseBody String admin() {
        return "admin";
    }

    @GetMapping("/manager")
    public @ResponseBody String manager() {
        return "manager";
    }

    // 스프링 시큐리티가 중간에 낚아채감. - SecurityConfig 설정 후 고침
    @GetMapping("/loginForm")
    public String login() {
        return "loginForm";
    }

    @PostMapping("/join")
    public String join(User user) {
        System.out.println("user = " + user);
        userService.join(user);
        return "redirect:/loginForm";
    }

    @GetMapping("/joinForm")
    public String joinForm() {
        return "joinForm";
    }

    @GetMapping("/joinProc")
    public @ResponseBody String joinProc() {
        return "회원가입 완료!";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/info")
    public @ResponseBody String info() {
        return "개인정보";
    }

    @PreAuthorize("hasRole('ROLE_MANAGER') || hasRole('ROLE_ADMIN')")
    @GetMapping("/data")
    public @ResponseBody String data() {
        return "데이터정보";
    }
}
