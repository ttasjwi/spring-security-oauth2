package com.ttasjwi.client.controller

import com.ttasjwi.client.controller.dto.PhotosRequest
import com.ttasjwi.client.domain.Photo
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient
import org.springframework.security.oauth2.core.OAuth2AccessToken
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestOperations

@RestController
class ApiController(
    private val restOperations: RestOperations
){

    @GetMapping("/token")
    fun token(@RegisteredOAuth2AuthorizedClient("keycloak") oAuth2AuthorizedClient: OAuth2AuthorizedClient): OAuth2AccessToken {
        return oAuth2AuthorizedClient.accessToken
    }

    @GetMapping("/photos")
    fun getPhotos(@ModelAttribute photosRequest: PhotosRequest): List<Photo> {
        val token = photosRequest.token

        val header = HttpHeaders()
        header.add("Authorization", "Bearer $token")
        val entity: HttpEntity<*> = HttpEntity<Any>(header)
        val url = "http://localhost:8082/photos"
        val response = restOperations.exchange(
            url,
            HttpMethod.GET,
            entity,
            object : ParameterizedTypeReference<List<Photo>>() {})
        return response.body!!
    }
}
