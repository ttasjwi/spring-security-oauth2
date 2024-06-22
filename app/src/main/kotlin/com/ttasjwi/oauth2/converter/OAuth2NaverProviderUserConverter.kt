package com.ttasjwi.oauth2.converter

import com.ttasjwi.oauth2.model.users.ProviderUser
import com.ttasjwi.oauth2.model.users.social.SocialType
import com.ttasjwi.oauth2.model.users.social.NaverOAuth2User

class OAuth2NaverProviderUserConverter : ProviderUserConverter {

    override fun convert(providerUserRequest: ProviderUserRequest): ProviderUser? {
        if (providerUserRequest.clientRegistration!!.registrationId != SocialType.NAVER.socialName) {
            return null
        }
        return NaverOAuth2User(providerUserRequest.oAuth2User!!, providerUserRequest.clientRegistration)
    }
}
