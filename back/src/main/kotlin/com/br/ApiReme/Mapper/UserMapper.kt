package com.br.ApiReme.Mapper

import com.br.ApiReme.Dtos.Reponse.User.UserDtoResponse
import com.br.ApiReme.Dtos.Request.User.UserDtoRequest
import com.br.ApiReme.domain.UserDomain
import org.springframework.stereotype.Component

@Component
object UserMapper {
    fun toEntity(dto: UserDtoRequest): UserDomain =
        UserDomain(
            name = dto.name,
            email = dto.email,
            passwordHash = dto.password,
        )

    fun toResponse(user: UserDomain): UserDtoResponse =
        UserDtoResponse(
            id = user.id,
            name = user.name,
            email = user.email
        )
}