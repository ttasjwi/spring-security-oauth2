package com.ttasjwi.oauth2.converter

import com.ttasjwi.oauth2.model.users.ProviderUser
import com.ttasjwi.oauth2.model.users.social.SocialType
import com.ttasjwi.oauth2.model.users.social.GoogleOidcUser

class OAuth2GoogleProviderUserConverter : ProviderUserConverter {

    override fun convert(providerUserRequest: ProviderUserRequest): ProviderUser? {
        if (providerUserRequest.clientRegistration!!.registrationId != SocialType.GOOGLE.socialName) {
            return null
        }
        return GoogleOidcUser(providerUserRequest.oAuth2User!!, providerUserRequest.clientRegistration)
    }
}
