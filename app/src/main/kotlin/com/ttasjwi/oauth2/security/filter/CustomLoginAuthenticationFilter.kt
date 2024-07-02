package com.ttasjwi.oauth2.security.filter

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.ttasjwi.oauth2.security.dto.LoginRequest
import com.ttasjwi.oauth2.security.signature.TokenSigner
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.util.matcher.RequestMatcher

class CustomLoginAuthenticationFilter(
    requestMatcher: RequestMatcher,
    private val tokenSigner: TokenSigner,
) : AbstractAuthenticationProcessingFilter(requestMatcher) {

    companion object {
        private val objectMapper: ObjectMapper = jacksonObjectMapper()
    }

    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        val loginRequest: LoginRequest
        try {
            loginRequest = objectMapper.readValue(request.inputStream, LoginRequest::class.java)
        } catch (e: Exception) {
            throw BadCredentialsException("자격증명이 유효하지 않습니다.")
        }
        val authenticationToken = UsernamePasswordAuthenticationToken(loginRequest.username, loginRequest.password)
        val authentication = authenticationManager.authenticate(authenticationToken)
        return authentication
    }

    override fun successfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain,
        authResult: Authentication
    ) {
        val user = authResult.principal as User
        val jwt = tokenSigner.signToken(user)
        response.addHeader("Authorization", "Bearer $jwt")
    }
}
