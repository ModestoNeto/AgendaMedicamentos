package com.br.ApiReme.` Port`.saida

import com.br.ApiReme.domain.UserDomain

interface UserPortS {
    fun save(user: UserDomain): UserDomain
    fun findById(id: Long): UserDomain?
    fun findAll(): List<UserDomain>
    fun deleteById(id: Long)
    fun findByEmail(email: String): UserDomain?
}