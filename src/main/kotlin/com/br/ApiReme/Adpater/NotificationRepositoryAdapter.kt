package com.br.ApiReme.Adapter


import com.br.ApiReme.` Port`.saida.NotificationPortS
import com.br.ApiReme.domain.Notification
import com.br.ApiReme.repository.NotificationRepository
import org.springframework.stereotype.Component

@Component
class NotificationRepositoryAdapter(
    private val notificationRepository: NotificationRepository
) : NotificationPortS {

    override fun save(notification: Notification): Notification =
        notificationRepository.save(notification)

    override fun findById(id: Long): Notification? =
        notificationRepository.findById(id).orElse(null)

    override fun findByReminderId(reminderId: Long): List<Notification> =
        notificationRepository.findByReminderId(reminderId)

    override fun findAll(): List<Notification> =
        notificationRepository.findAll()

    override fun deleteById(id: Long) =
        notificationRepository.deleteById(id)
}
