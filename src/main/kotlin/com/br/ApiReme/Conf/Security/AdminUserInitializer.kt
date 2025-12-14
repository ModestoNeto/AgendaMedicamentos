package com.br.ApiReme.Conf.Security

import com.br.ApiReme.domain.Role
import com.br.ApiReme.domain.UserDomain
import com.br.ApiReme.repository.UserRepository
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class AdminUserInitializer {

    @Bean
    fun createAdminUser(
        userRepository: UserRepository,
        passwordEncoder: PasswordEncoder
    ) = ApplicationRunner {

        val email = "admin@apireme.com"

        val exists = userRepository.findByEmail(email)
        if (exists == null) {
            val admin = UserDomain(
                name = "Administrador",
                email = email,
                passwordHash = passwordEncoder.encode("admin123"),
                role = Role.ADMIN
            )

            userRepository.save(admin)
        }
    }
}