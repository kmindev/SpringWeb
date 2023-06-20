package hello.hellospring.controller;

import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @Component를 포함하는 어노테이션은 스프링 빈으로 자동 등록된다.
 * @Controller, @Service, @Repository 등은 @Component 를 가지고 있다.
 * @SpringBootApplication 존재하는 클래스(Main 클래스) 패키지에 존재하는 @Component 만 빈으로 등록된다.
 */
@Controller
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
}
