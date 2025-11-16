package com.br.ApiReme.` Port`.entrada

import com.br.ApiReme.Dtos.Reponse.User.UserDtoResponse
import com.br.ApiReme.Dtos.Request.User.UserDtoRequest
import com.br.ApiReme.Dtos.Request.User.UserUpdateDtoRequest
import com.br.ApiReme.domain.UserDomain




interface UserPortE{
    fun createUser(user: UserDtoRequest): UserDtoResponse
    fun findUserById(id: Long): UserDtoResponse
    fun findAllUsers(): List<UserDtoResponse>
    fun updateUser(id: Long, updatedUser: UserUpdateDtoRequest): UserDtoResponse
    fun deleteUser(id: Long)
}

