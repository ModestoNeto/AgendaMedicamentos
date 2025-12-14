package com.br.ApiReme.Conf.Security


import com.br.ApiReme.` Port`.saida.TokenPortS
import org.springframework.security.oauth2.jwt.JwtClaimsSet
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.JwtEncoderParameters
import org.springframework.stereotype.Component
import java.time.Instant

@Component
class JwtTokenAdapter(
    private val jwtEncoder: JwtEncoder
) : TokenPortS {

    override fun generateToken(
        subject: String,
        roles: List<String>
    ): String {

        val now = Instant.now()

        val claims = JwtClaimsSet.builder()
            .issuer("api-reme")
            .subject(subject)
            .issuedAt(now)
            .expiresAt(now.plusSeconds(3600))
            .claim("roles", roles)
            .build()

        return jwtEncoder
            .encode(JwtEncoderParameters.from(claims))
            .tokenValue
    }
}