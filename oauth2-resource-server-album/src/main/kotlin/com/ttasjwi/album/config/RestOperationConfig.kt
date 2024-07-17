package com.ttasjwi.album.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestOperations
import org.springframework.web.client.RestTemplate

@Configuration
class RestOperationConfig {

    @Bean
    fun restOperations(): RestOperations {
        return RestTemplate()
    }
}
