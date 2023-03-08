package com.ttasjwi.oauth2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@Slf4j
public class CustomSecurityConfigurer extends AbstractHttpConfigurer<CustomSecurityConfigurer, HttpSecurity> {

    private boolean isSecure;

    public CustomSecurityConfigurer(boolean isSecure) {
        this.isSecure = isSecure;
    }

    @Override
    public void init(HttpSecurity builder) throws Exception {
        super.init(builder);
        log.info("configure method started.");
    }

    @Override
    public void configure(HttpSecurity builder) throws Exception {
        super.configure(builder);
        log.info("configure method started..");
        if (isSecure) {
            log.info("https is required...");
        } else {
            log.info("https is required...");
        }
    }

    public CustomSecurityConfigurer enableSecure() {
        this.isSecure = true;
        return this;
    }

    public CustomSecurityConfigurer disableSecure() {
        this.isSecure = false;
        return this;
    }
}
