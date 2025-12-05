package com.br.ApiReme.repository


import com.br.ApiReme.domain.Notification
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface NotificationRepository : JpaRepository<Notification, Long> {
    fun findByReminderId(reminderId: Long): List<Notification>
}
