package com.ttasjwi.oauth2

import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer

class CustomSecurityConfigurer(
    private var isSecure: Boolean = false,
    private var someConfig: Boolean = false,
): AbstractHttpConfigurer<CustomSecurityConfigurer, HttpSecurity>() {

    override fun init(builder: HttpSecurity) {
        super.init(builder)
        println("init method started...")
    }

    override fun configure(builder: HttpSecurity) {
        super.configure(builder)
        println("configure method started...")

        if (isSecure) {
            println("Https is required")
        } else {
            println("https is optional")
        }
    }

    fun setFlag(isSecure: Boolean): CustomSecurityConfigurer {
        this.isSecure = isSecure
        return this
    }

    fun setSomeConfig(someConfig: Boolean) : CustomSecurityConfigurer {
        this.someConfig = someConfig
        return this
    }
}
