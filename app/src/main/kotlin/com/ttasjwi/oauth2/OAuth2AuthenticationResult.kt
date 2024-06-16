package com.ttasjwi.oauth2

class OAuth2AuthenticationResult(
    val username: String,
    val name: String,
    val roles: Collection<String>,
    val clientId: String,
    val accessToken: String,
    val refreshToken: String,
)
