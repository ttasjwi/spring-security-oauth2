package com.ttasjwi.oauth2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(request -> request.anyRequest().authenticated()); // 모든 요청에 대해 인증
        http.formLogin(); // 폼 로그인
        http.apply(new CustomSecurityConfigurer(true)); // 초기화 대상 SecurityConfigurer 등록
        return http.build();
    }
}
