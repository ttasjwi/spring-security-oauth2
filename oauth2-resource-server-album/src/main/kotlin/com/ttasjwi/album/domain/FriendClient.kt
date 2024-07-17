package com.ttasjwi.album.domain

import com.ttasjwi.common.domain.Friend
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.stereotype.Component
import org.springframework.web.client.RestOperations

@Component
class FriendClient(
    private val restOperations: RestOperations,
) {

    fun loadFriends(): List<Friend> {
        val header = HttpHeaders()
        val authentication =
            SecurityContextHolder.getContextHolderStrategy().context.authentication as JwtAuthenticationToken
        header.add("Authorization", "Bearer ${authentication.token.tokenValue}")

        val url = "http://127.0.0.1:8083/friends"
        val entity = HttpEntity<Any>(header)
        val response: ResponseEntity<List<Friend>> = restOperations.exchange(url, HttpMethod.GET, entity,
            object : ParameterizedTypeReference<List<Friend>>() {})

        return response.body!!
    }
}
