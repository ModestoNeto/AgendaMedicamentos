package com.br.ApiReme.service

import com.br.ApiReme.domain.UserDomain
import com.br.ApiReme.` Port`.saida.UserPortS
import com.br.ApiReme.` Port`.entrada.UserPortE
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserPortS
) : UserPortE {

    override fun createUser(user: UserDomain): UserDomain =
        userRepository.save(user)

    override fun findUserById(id: Long): UserDomain? =
        userRepository.findById(id)

    override fun findAllUsers(): List<UserDomain> =
        userRepository.findAll()

    override fun updateUser(id: Long, updatedUser: UserDomain): UserDomain {
        val existingUser = userRepository.findById(id)
            ?: throw IllegalArgumentException("Usuário com ID $id não encontrado")

        val newUser = existingUser.copy(
            name = updatedUser.name,
            email = updatedUser.email,
            passwordHash = updatedUser.passwordHash
        )
        return userRepository.save(newUser)
    }

    override fun deleteUser(id: Long) {
        userRepository.deleteById(id)
    }
}
