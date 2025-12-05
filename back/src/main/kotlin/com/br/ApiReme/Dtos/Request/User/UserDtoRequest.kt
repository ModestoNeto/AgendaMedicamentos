package com.br.ApiReme.Dtos.Request.User

data class UserDtoRequest(
    val name: String,
    val email: String,
    val password: String
)