package com.ttasjwi.oauth2.converter

import com.ttasjwi.oauth2.model.users.ProviderUser
import com.ttasjwi.oauth2.model.users.social.SocialType
import com.ttasjwi.oauth2.model.users.social.KakaoOAuth2User
import com.ttasjwi.oauth2.model.users.social.KakaoOidcUser
import org.springframework.security.oauth2.core.oidc.user.OidcUser

class OAuth2KakaoProviderUserConverter: ProviderUserConverter {

    override fun convert(providerUserRequest: ProviderUserRequest): ProviderUser? {
        if (providerUserRequest.clientRegistration!!.registrationId != SocialType.KAKAO.socialName) {
            return null
        }
        if (providerUserRequest.oAuth2User is OidcUser) {
            return KakaoOidcUser(providerUserRequest.oAuth2User, providerUserRequest.clientRegistration)
        }
        return KakaoOAuth2User(providerUserRequest.oAuth2User!!, providerUserRequest.clientRegistration)
    }
}
