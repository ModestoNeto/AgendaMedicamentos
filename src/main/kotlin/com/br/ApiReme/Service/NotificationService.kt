package com.br.ApiReme.service

import Notification
import com.br.ApiReme.` Port`.entrada.NotificatePortE
import com.br.ApiReme.` Port`.saida.NotificationPortS
import org.springframework.stereotype.Service

@Service
class NotificationService(
    private val notificationRepository: NotificationPortS
) : NotificatePortE {

    override fun createNotification(notification: Notification): Notification =
        notificationRepository.save(notification)

    override fun findNotificationById(id: Long): Notification? =
        notificationRepository.findById(id)

    override fun findNotificationsByReminder(reminderId: Long): List<Notification> =
        notificationRepository.findByReminderId(reminderId)
    override fun findAllNotifications(): List<Notification> =
        notificationRepository.findAll()

    override fun updateNotification(id: Long, updatedNotification: Notification): Notification {
        val existing = notificationRepository.findById(id)
            ?: throw IllegalArgumentException("Notificação com ID $id não encontrada")

        val newNotification = existing.copy(
            sentAt = updatedNotification.sentAt,
            status = updatedNotification.status
        )
        return notificationRepository.save(newNotification)
    }

    override fun deleteNotification(id: Long) {
        notificationRepository.deleteById(id)
    }
}
