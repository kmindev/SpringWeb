package com.cos.jwtserver.config;

import com.cos.jwtserver.jwt.JwtAuthenticationFilter;
import com.cos.jwtserver.filter.MyFilter3;
import com.cos.jwtserver.jwt.JwtAuthorizationFilter;
import com.cos.jwtserver.ropository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.CorsFilter;


@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인에 등록이 된다.
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {
    private final CorsConfig corsConfig;
    private final UserRepository userRepository;
    /**
     * /user: 인증받아야 접근 가능
     * /manger: admin, manger 권한이 있어야 접근 가능
     * /admin: admin 권한이 있어야 접근 가능
     */

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 사용 x
                .addFilter(corsConfig.corsFilter())
                .addFilter(new JwtAuthenticationFilter(http.getSharedObject(AuthenticationManager.class)))
                .addFilter(new JwtAuthorizationFilter(http.getSharedObject(AuthenticationManager.class), userRepository))
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/api/v1/user/**")).hasAnyRole(new String[]{"USER", "MANAGER", "ADMIN"})
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/api/v1/manager/**")).hasAnyRole(new String[] {"MANAGER", "ADMIN"})
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/api/v1/admin/**")).hasRole("ADMIN")
                        .anyRequest().permitAll()
                );

        return http.build();
    }

//    public class MyCustomDsl extends AbstractHttpConfigurer<MyCustomDsl, HttpSecurity> {
//        @Override
//        public void configure(HttpSecurity http) throws Exception {
//            AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
//            http
//                    .addFilter(corsConfig.corsFilter())
//                    .addFilter(new JwtAuthenticationFilter(authenticationManager))
//                    .addFilter(new JwtAuthorizationFilter(authenticationManager, userRepository))
//                    .build();
//        }
//    }
}
