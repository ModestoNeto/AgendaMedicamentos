package com.br.ApiReme.` Port`.entrada

import com.br.ApiReme.domain.UserDomain




interface UserPortE{
    fun createUser(user: UserDomain): UserDomain
    fun findUserById(id: Long): UserDomain?
    fun findAllUsers(): List<UserDomain>
    fun updateUser(id: Long, updatedUser: UserDomain): UserDomain
    fun deleteUser(id: Long)
}

