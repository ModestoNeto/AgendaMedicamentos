package com.br.ApiReme.service


import com.br.ApiReme.` Port`.entrada.NotificatePortE
import com.br.ApiReme.` Port`.saida.NotificationPortS
import com.br.ApiReme.Dtos.Reponse.Notification.NotificationDtoResponse
import com.br.ApiReme.Dtos.Request.Notification.NotificationDtoRequest
import com.br.ApiReme.Dtos.Request.Notification.NotificationUpdateDtoRequest
import com.br.ApiReme.domain.Notification
import com.br.ApiReme.Mapper.NotificationMapper
import com.br.ApiReme.domain.NotificationStatus
import org.springframework.stereotype.Service
import kotlin.streams.toList

@Service
class NotificationService(
    private val notificationRepository: NotificationPortS,
    private val  notificationMapper : NotificationMapper
) : NotificatePortE {

    override fun createNotification(notificationDto: NotificationDtoRequest): NotificationDtoResponse {
        val notificaSalve = notificationMapper.toEntity(notificationDto,null)


        return  notificationMapper.toDto(notificationRepository.save(notificaSalve))

    }

    override fun findNotificationById(id: Long): NotificationDtoResponse? {
       val findNotificationByIdEnti = notificationRepository.findById(id)
           ?:throw IllegalArgumentException("Notificate com ID  não encontrada")
        return notificationMapper.toDto(findNotificationByIdEnti)
    }


    override fun findNotificationsByReminder(reminderId: Long): List<NotificationDtoResponse> {

        return   notificationRepository.findByReminderId(reminderId).stream().map { eti -> notificationMapper.toDto(eti) }.toList()

    }

    override fun findAllNotifications(): List<NotificationDtoResponse> {
       return  notificationRepository.findAll().stream().map { eti -> notificationMapper.toDto(eti) }.toList()
    }


    override fun updateNotification(id: Long, updatedNotification: NotificationUpdateDtoRequest): NotificationDtoResponse {
        var existing = notificationRepository.findById(id)
            ?: throw IllegalArgumentException("Notificação com ID $id não encontrada")

        existing.messagem = updatedNotification.message
        existing.status = NotificationStatus.valueOf(updatedNotification.status!!.uppercase())



        return notificationMapper.toDto(notificationRepository.save(existing))
    }

    override fun deleteNotification(id: Long) {
        notificationRepository.deleteById(id)
    }
}
