package com.ttasjwi.resourceserver.controller.dto

import org.springframework.security.core.Authentication

class OpaqueDto (
    val isActive: Boolean,
    val authentication: Authentication,
    val principal: Any,
)
