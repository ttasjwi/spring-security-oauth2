package com.ttasjwi.oauth2.controller.dto

import org.springframework.security.core.Authentication

class OpaqueTokenResponse (
    val active: Boolean,
    val principal: Any,
    val authentication: Authentication
)
