package com.ttasjwi.oauth2.model.users

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.oauth2.core.oidc.OidcIdToken
import org.springframework.security.oauth2.core.oidc.OidcUserInfo
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.security.oauth2.core.user.OAuth2User

class AuthUser(val providerUser: ProviderUser): UserDetails, OidcUser, OAuth2User {

    override fun getName(): String {
        return providerUser.username
    }

    override fun getAttributes(): Map<String, Any> {
        return providerUser.attributes
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return providerUser.authorities
    }

    override fun getClaims(): MutableMap<String, Any> {
        return mutableMapOf()
    }

    override fun getUserInfo(): OidcUserInfo? {
        return null
    }

    override fun getIdToken(): OidcIdToken? {
        return null
    }

    override fun getPassword(): String {
        return providerUser.password
    }

    override fun getUsername(): String {
        return providerUser.username
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

}
