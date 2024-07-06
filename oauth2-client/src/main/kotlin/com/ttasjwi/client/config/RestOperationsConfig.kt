package com.ttasjwi.client.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestOperations
import org.springframework.web.client.RestTemplate

@Configuration
class RestOperationsConfig {

    @Bean
    fun restOperations(): RestOperations {
        return RestTemplate()
    }
}
