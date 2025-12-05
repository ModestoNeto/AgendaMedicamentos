package com.br.ApiReme.repository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import com.br.ApiReme.domain.UserDomain

@Repository
interface UserRepository  : JpaRepository<UserDomain, Long> {
    fun findByEmail(email: String): UserDomain?
}