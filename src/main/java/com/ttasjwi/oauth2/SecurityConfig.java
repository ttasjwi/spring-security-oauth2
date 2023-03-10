package com.ttasjwi.oauth2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests().anyRequest().authenticated();
        // 인증방식 미지정 -> AuthenticationEntryPoint 미등록 -> Http403ForbiddenEntryPoint 등록 -> 무조건 403
        return http.build();
    }
}
