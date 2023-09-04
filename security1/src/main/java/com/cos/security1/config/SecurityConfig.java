package com.cos.security1.config;

import com.cos.security1.config.outh.PrincipalOauth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// 구글 로그인 연동 + 회원가입 과정
// 1. 코드받기(인증), 2. 엑세스토큰(권한), 3.사용자프로필 정보를 가져옴. 4. 그 정보를 토대로 회원가입을 자동으로 진행(필요한 정보가 있으면 추가로 받아야 함.)

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인에 등록이 된다.
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {
    private final PrincipalOauth2UserService principalOauth2UserService;

    /**
     * /user: 인증받아야 접근 가능
     * /manger: admin, manger 권한이 있어야 접근 가능
     * /admin: admin 권한이 있어야 접근 가능
     */
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/user/**").authenticated() // 인증만 되면 접근 가능
                .requestMatchers("/manager/**").hasAnyRole("MANAGER", "ADMIN")
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().permitAll()
        );

        http.formLogin(form -> form
                .loginPage("/loginForm")
                .loginProcessingUrl("/login") // login 주소가 호출이 되면 시큐리티가 낚아채서 대신 로그인을 진행
                .defaultSuccessUrl("/")
        );

        // 구글 로그인 완료 후 후처리. Tip. 코드X, (엑세스토큰 + 사용자프로필정보 O)
        // https://docs.spring.io/spring-security/reference/servlet/oauth2/login/advanced.html 참고
        http.oauth2Login(form -> form
                .loginPage("/loginForm")
                .userInfoEndpoint(userInfo -> userInfo
                        .userService(principalOauth2UserService))
        );

        return http.build();
    }
}