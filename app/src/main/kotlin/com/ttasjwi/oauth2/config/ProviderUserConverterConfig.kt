package com.ttasjwi.oauth2.config

import com.ttasjwi.oauth2.converter.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ProviderUserConverterConfig {

    @Bean
    fun providerUserConverter(): ProviderUserConverter {
        return DelegatingProviderUserConverter(
            listOf(
                OAuth2GoogleProviderUserConverter(),
                OAuth2NaverProviderUserConverter(),
                OAuth2KakaoProviderUserConverter()
            )
        )
    }
}
