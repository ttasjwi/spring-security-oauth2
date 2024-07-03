package com.ttasjwi.oauth2.security.filter

import com.ttasjwi.oauth2.security.filter.JwtAuthenticationFilter.Companion.log
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.web.filter.OncePerRequestFilter

class JwtDecoderAuthenticationFilter(
    private val jwtDecoder: JwtDecoder
) : OncePerRequestFilter() {


    private val bearerTokenHeaderName = HttpHeaders.AUTHORIZATION


    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain
    ) {
        val token = resolveToken(request)

        if (token == null) {
            chain.doFilter(request, response)
            return
        }
        val jwt = jwtDecoder.decode(token)

        val username = jwt.getClaimAsString("username")
        val authorities = jwt.getClaimAsStringList("authority")

        if (username != null && authorities != null) {
            val user = User.withUsername(username).password("passwd").authorities(authorities.map { SimpleGrantedAuthority(it) }).build()
            val auth = UsernamePasswordAuthenticationToken.authenticated(user, null, user.authorities)
            SecurityContextHolder.getContextHolderStrategy().context.authentication = auth
        }
        chain.doFilter(request, response)
    }

    private fun resolveToken(request: HttpServletRequest): String? {
        val header = request.getHeader(bearerTokenHeaderName)
        if (header == null || !header.startsWith("Bearer ")) {
            return null
        }
        log.info { "header: $header" }
        return header.replace("Bearer ", "")
    }
}
