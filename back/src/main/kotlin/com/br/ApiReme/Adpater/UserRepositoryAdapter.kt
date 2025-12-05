package com.br.ApiReme.Adapter


import org.springframework.stereotype.Component
import com.br.ApiReme.` Port`.saida.UserPortS
import com.br.ApiReme.domain.UserDomain
import com.br.ApiReme.repository.UserRepository

@Component
class UserRepositoryAdapter(
    private val userRepository: UserRepository
) : UserPortS {

    override fun save(user: UserDomain): UserDomain =
        userRepository.save(user)

    override fun findById(id: Long): UserDomain? =
        userRepository.findById(id).orElse(null)

    override fun findAll(): List<UserDomain> =
        userRepository.findAll()

    override fun deleteById(id: Long) =
        userRepository.deleteById(id)

    override fun findByEmail(email: String): UserDomain? =
        userRepository.findByEmail(email)

}
