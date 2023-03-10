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

        // 여러개의 DefaultAuthenticationEntryPoint -> 상황에 맞는 것 사용
        http.httpBasic();
        http.formLogin();
        return http.build();
    }
}
