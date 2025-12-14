package com.br.ApiReme.Conf.Security


import com.br.ApiReme.` Port`.entrada.AuthPortE
import com.br.ApiReme.` Port`.saida.TokenPortS
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val authenticationManager: AuthenticationManager,
    private val tokenPort: TokenPortS
) : AuthPortE {

    override fun login(email: String, password: String): String {

        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(email, password)
        )

        SecurityContextHolder.getContext().authentication = authentication

        val roles = authentication.authorities.map { it.authority }

        return tokenPort.generateToken(
            subject = authentication.name,
            roles = roles
        )
    }
}