package com.br.ApiReme.` Port`.entrada

import com.br.ApiReme.Dtos.Reponse.Notification.NotificationDtoResponse
import com.br.ApiReme.Dtos.Request.Notification.NotificationDtoRequest
import com.br.ApiReme.Dtos.Request.Notification.NotificationUpdateDtoRequest
import com.br.ApiReme.domain.Notification


interface NotificatePortE {


        fun createNotification(notification: NotificationDtoRequest): NotificationDtoResponse
        fun findNotificationById(id: Long): NotificationDtoResponse?
        fun findNotificationsByReminder(reminderId: Long): List<NotificationDtoResponse>
        fun updateNotification(id: Long, updatedNotification: NotificationUpdateDtoRequest): NotificationDtoResponse
        fun deleteNotification(id: Long)
        fun findAllNotifications(): List<NotificationDtoResponse>
}