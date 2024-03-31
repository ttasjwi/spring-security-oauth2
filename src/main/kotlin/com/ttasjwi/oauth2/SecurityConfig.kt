package com.ttasjwi.oauth2

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.web.SecurityFilterChain


@EnableWebSecurity
@Configuration
class SecurityConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        // 여기서 주입된 HttpSecurity는 스프링 시큐리티가 제공하는 프로토타입 빈이다.
        // 이미 기본적인 설정이 어느 정도 되어 있고, 우리가 등록한 추가 설정이 여기에 더해진다.
        http {
            authorizeHttpRequests {
                authorize(anyRequest, authenticated)
            }
            formLogin {  }
            apply(
                // 별도의 커스텀 Configurer를 만들고 나만의 DSL을 구성할 수 있다.
                CustomSecurityConfigurer()
                    .setFlag(false)
                    .setSomeConfig(true)
            )
        }

        // SecurityFilterChain을 구성한다.
        return http.build()
    }
}
