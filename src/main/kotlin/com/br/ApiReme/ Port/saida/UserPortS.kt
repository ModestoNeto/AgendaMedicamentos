package com.br.ApiReme.` Port`.saida
import UserDomain
interface UserPortS {
    fun save(user: UserDomain): UserDomain
    fun findById(id: Long): UserDomain?
    fun findAll(): List<UserDomain>
    fun deleteById(id: Long)
}