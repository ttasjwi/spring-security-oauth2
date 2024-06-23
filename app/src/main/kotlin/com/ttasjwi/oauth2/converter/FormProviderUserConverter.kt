package com.ttasjwi.oauth2.converter

import com.ttasjwi.oauth2.model.users.ProviderUser
import com.ttasjwi.oauth2.model.users.form.FormProviderUser

class FormProviderUserConverter : ProviderUserConverter {

    override fun convert(providerUserRequest: ProviderUserRequest): ProviderUser? {
        val user = providerUserRequest.user ?: return null

        return FormProviderUser(
            id = user.id,
            username = user.username,
            password = user.password,
            email = user.email,
            provider = "none",
            authorities = user.authorities
        )
    }
}
