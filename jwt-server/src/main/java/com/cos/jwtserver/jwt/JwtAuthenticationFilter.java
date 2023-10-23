package com.cos.jwtserver.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.cos.jwtserver.auth.PrincipalDetails;

import com.cos.jwtserver.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.cos.jwtserver.auth.PrincipalDetails;


import java.io.IOException;
import java.util.Date;

// security에서 formLogin을 disable 했기 때문에

// 스프링 시큐리티에서 UsernamePasswordAuthenticationFilter가 있음
// /login 요청해서 username, password 전송하면 (post)
// UsernamePasswordAuthenticationFilter 동작을 함.
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    // /login 요청을 하면 로그인 시도를 위해서 실행되는 함수
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("JwtAuthenticationFilter : 로그인 시도중");

        // 1. username, password 받아서
        try {
//            BufferedReader br = request.getReader();
//            String input = null;
//            while ((input = br.readLine()) != null) {
//                System.out.println(input);
//            }

            // json -> User
            ObjectMapper om = new ObjectMapper();
            User user = om.readValue(request.getInputStream(), User.class);
            System.out.println(user);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());

            // PrincipalDetailsService의 loadUserByUsername() 메서드가 실행됨
            // DB에 있는 username과 password가 일치
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            // 로그인 되었다는 뜻
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            System.out.println(principalDetails.getUser().getUsername());

            // authentication 객체가 session 영역에 저장
            // return 이유는 권한 관리를 security가 대신해주기 때문
            // 굳이 jwt 토큰을 사용하면서 세션을 만들 이유는 없지만 security 권한 처리 떄문에 session에 저장.
            return authentication;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // attemptAuthentication 실행 후 인증이 정상적으로 되었으면 successfulAuthentication 함수가 실행
    // jwt 토큰을 만들어서 request 요청한 사용자에게 jwt 토큰을 response 해주면 됨
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        System.out.println("successfulAuthentication 실행됨 : 인증이 완료되었다는 뜻.");
        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();

        // Hash 암호방식
        String jwtToken = JWT.create()
                .withSubject("cos토큰")
                .withExpiresAt(new Date(System.currentTimeMillis() + (JwtProperties.EXPIRATION_TIME))) // 만료시간(10분)
                .withClaim("id", principalDetails.getUser().getUsername())
                .withClaim("username", principalDetails.getUsername())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));

        response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + jwtToken);
    }
}
