package com.ttasjwi.oauth2.security.authentication

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.core.DefaultOAuth2AuthenticatedPrincipal
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal
import org.springframework.security.oauth2.core.OAuth2TokenIntrospectionClaimNames
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector
import java.util.*

class CustomOpaqueTokenIntrospector(
    private val delegate: OpaqueTokenIntrospector
) : OpaqueTokenIntrospector {


    override fun introspect(token: String): OAuth2AuthenticatedPrincipal {
        val principal = delegate.introspect(token)

        val name = principal.name
        val attributes = principal.attributes
        val authorities = getAuthorities(attributes)

        return DefaultOAuth2AuthenticatedPrincipal(name, attributes, authorities)
    }

    private fun getAuthorities(attributes: Map<String, *>): MutableList<GrantedAuthority> {
        return (attributes[OAuth2TokenIntrospectionClaimNames.SCOPE] as List<String>)
            .map { SimpleGrantedAuthority("ROLE_"+ it.uppercase(Locale.getDefault())) }
            .toMutableList()
    }
}
