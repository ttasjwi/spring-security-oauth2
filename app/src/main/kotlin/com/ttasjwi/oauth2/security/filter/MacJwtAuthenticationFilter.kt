package com.ttasjwi.oauth2.security.filter

import com.nimbusds.jose.crypto.MACVerifier
import com.nimbusds.jose.jwk.OctetSequenceKey
import com.nimbusds.jwt.SignedJWT
import com.ttasjwi.oauth2.security.signature.JWKRepository
import com.ttasjwi.oauth2.support.logging.getLogger
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.web.filter.OncePerRequestFilter

class MacJwtAuthenticationFilter(
    private val jwkRepository: JWKRepository
) : OncePerRequestFilter() {

    private val bearerTokenHeaderName = HttpHeaders.AUTHORIZATION

    companion object {
        val log = getLogger(MacJwtAuthenticationFilter::class.java)
    }

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
        val signedJWT: SignedJWT
        try {
            signedJWT = SignedJWT.parse(token)
            val macVerifier = MACVerifier((jwkRepository.findJWK() as OctetSequenceKey).toSecretKey())
            val verify = signedJWT.verify(macVerifier)

            if (verify) {
                val jwtClaimSet = signedJWT.jwtClaimsSet
                val username = jwtClaimSet.getClaim("username")?.toString()
                val authorities = jwtClaimSet.getClaim("authority") as MutableList<String>?

                if (username != null && authorities != null) {
                    val user = User.withUsername(username).password("passwd").authorities(authorities.map { SimpleGrantedAuthority(it) }).build()
                    val auth = UsernamePasswordAuthenticationToken.authenticated(user, null, user.authorities)

                    SecurityContextHolder.getContextHolderStrategy().context.authentication = auth
                }
            }
        } catch(e: Exception) {
            log.error (e) { "토큰 검증 과정에서 예외 발생"  }
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
