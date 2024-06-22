package com.ttasjwi.oauth2.converter

import com.ttasjwi.oauth2.model.users.ProviderUser

interface ProviderUserConverter {

    fun convert(providerUserRequest: ProviderUserRequest): ProviderUser?
}
