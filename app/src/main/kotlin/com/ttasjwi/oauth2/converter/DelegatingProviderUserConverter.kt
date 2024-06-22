package com.ttasjwi.oauth2.converter

import com.ttasjwi.oauth2.model.users.ProviderUser

class DelegatingProviderUserConverter(
    private val converters: List<ProviderUserConverter>
) : ProviderUserConverter {

    override fun convert(providerUserRequest: ProviderUserRequest): ProviderUser? {
        for (converter in converters) {
            val providerUser = converter.convert(providerUserRequest)
            if (providerUser != null) {
                return providerUser
            }
        }
        return null
    }
}
