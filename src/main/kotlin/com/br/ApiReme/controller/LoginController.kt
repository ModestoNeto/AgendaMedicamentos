package com.br.ApiReme.controller

import com.br.ApiReme.` Port`.entrada.AuthPortE
import com.br.ApiReme.Dtos.Reponse.User.LoginResponse
import com.br.ApiReme.Dtos.Request.User.LoginRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/login")
class LoginController(
    private val authPort: AuthPortE
) {

    @PostMapping
    fun login(@RequestBody request: LoginRequest): LoginResponse {
        val token = authPort.login(
            request.email,
            request.password
        )
        return LoginResponse(token)
    }
}