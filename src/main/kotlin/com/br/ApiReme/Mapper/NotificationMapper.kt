package com.br.ApiReme.Mapper

import com.br.ApiReme.Dtos.Reponse.Notification.NotificationDtoResponse
import com.br.ApiReme.Dtos.Request.Notification.NotificationDtoRequest
import com.br.ApiReme.domain.Notification
import com.br.ApiReme.domain.NotificationStatus
import com.br.ApiReme.domain.Reminder
import org.springframework.stereotype.Component

@Component
object NotificationMapper {
    fun toEntity(dto: NotificationDtoRequest, reminder: Reminder?): Notification =
        Notification(
            reminder = reminder,
            sentAt = null,
            status = NotificationStatus.valueOf(dto.status.uppercase()),
            messagem = dto.message
        )

    fun toDto(entity: Notification): NotificationDtoResponse =
        NotificationDtoResponse(
            id = entity.id,
            reminderId = entity.reminder?.id,
            sentAt = entity.sentAt?.toString()!!,
            status = entity.status.toString(),
            message = entity.messagem!!
        )

}