package com.cos.security1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // view를 리턴한다.
public class IndexController {

    @GetMapping({"", "/"})
    public String index() {
        // 머스테치 (템플릿엔진) 기본경로 src/main/resources/
        // view resolver 설정: templates (prefix), .mustache (suffix) 생략 가능
        return "index";
    }
}
