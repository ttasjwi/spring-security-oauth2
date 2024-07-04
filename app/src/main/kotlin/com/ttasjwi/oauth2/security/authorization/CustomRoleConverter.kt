package com.ttasjwi.oauth2.security.authorization

import org.springframework.core.convert.converter.Converter
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.jwt.Jwt

class CustomRoleConverter :
    Converter<Jwt, MutableCollection<GrantedAuthority>> {

    private val rolePrefix = "ROLE_"

    override fun convert(jwt: Jwt): MutableCollection<GrantedAuthority> {
        return getAuthorities(jwt)
            .map { SimpleGrantedAuthority(rolePrefix + it) }
            .toMutableList()
    }

    private fun getAuthorities(jwt: Jwt): MutableList<String> {
        val authorities = mutableListOf<String>()

        jwt.getClaimAsString("scope")?.split(" ")?.run {
            authorities.addAll(this)
        }

        jwt.getClaim<Map<String, List<String>>>("realm_access")?.get("roles")?.run {
            authorities.addAll(this)
        }
        return authorities
    }

}
