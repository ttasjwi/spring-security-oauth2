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

        http.httpBasic();
        http.formLogin();
        // 커스텀 AuthenticationEntryPoint 지정 -> 가장 우선시 된다.
        http.exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint());

        return http.build();
    }
}
