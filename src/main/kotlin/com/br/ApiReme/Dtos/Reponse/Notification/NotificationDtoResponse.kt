package com.br.ApiReme.Dtos.Reponse.Notification

import java.time.LocalDateTime

data class NotificationDtoResponse(
    val id: Long,
    val reminderId: Long?,
    val message: String,
    val status: String,
    val sentAt: String
)
