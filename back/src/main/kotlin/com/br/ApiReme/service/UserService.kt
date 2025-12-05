package com.br.ApiReme.service

import com.br.ApiReme.domain.UserDomain
import com.br.ApiReme.` Port`.saida.UserPortS
import com.br.ApiReme.` Port`.entrada.UserPortE
import com.br.ApiReme.Dtos.Reponse.User.UserDtoResponse
import com.br.ApiReme.Dtos.Request.User.UserDtoRequest
import com.br.ApiReme.Dtos.Request.User.UserUpdateDtoRequest
import com.br.ApiReme.Mapper.UserMapper
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserPortS,
    private val usermapper: UserMapper
) : UserPortE {

    override fun createUser(user: UserDtoRequest): UserDtoResponse {
        val createUserEti = usermapper.toEntity(user)
         return usermapper.toResponse(userRepository.save(createUserEti))

    }

    override fun findUserById(id: Long): UserDtoResponse {
       val fidUserEnti = userRepository.findById(id)
           ?: throw IllegalArgumentException("User com ID $id não encontrado")
        return usermapper.toResponse(fidUserEnti)
    }

    override fun findAllUsers(): List<UserDtoResponse> {
       return userRepository.findAll().stream().map { etiFind -> usermapper.toResponse(etiFind) }.toList()
    }

    override fun updateUser(id: Long, updatedUser: UserUpdateDtoRequest): UserDtoResponse {
        var existingUser = userRepository.findById(id)
            ?: throw IllegalArgumentException("Usuário com ID $id não encontrado")

        existingUser.name=updatedUser.name!!
        existingUser.email=updatedUser.email!!

        return usermapper.toResponse(userRepository.save(existingUser))
    }

    override fun deleteUser(id: Long) {
        userRepository.deleteById(id)
    }
}
