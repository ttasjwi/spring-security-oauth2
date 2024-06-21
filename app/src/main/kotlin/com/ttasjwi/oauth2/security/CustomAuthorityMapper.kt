package com.ttasjwi.oauth2.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper

class CustomAuthorityMapper(
    private val prefix: String = "ROLE_"
) : GrantedAuthoritiesMapper {

    override fun mapAuthorities(authorities: MutableCollection<out GrantedAuthority>): MutableCollection<out GrantedAuthority> {
        val mapped = HashSet<GrantedAuthority>(authorities.size)
        for (authority in authorities) {
            mapped.add(mapAuthority(authority.authority))
        }
        return mapped
    }

    // 구글 scope name: 'http://google.com/afadf/ㅁㅇㄻㅇㄹ.profile, ...
    // keycloak scope name: 'profile' ...
    // 제각각 사양이 다르므로 이에 맞추기위함
    private fun mapAuthority(name: String): GrantedAuthority {
        val dotIndex = name.lastIndexOf(".")
        var name = name
        if (dotIndex >= 0) {
            name = name.substring(dotIndex + 1)
        }
        if (this.prefix.isNotEmpty() && !name.startsWith(this.prefix)) {
            name = this.prefix + name
        }
        return SimpleGrantedAuthority(name)
    }

}
