package com.br.ApiReme.Dtos.Reponse.Notification

data class NotificationDtoResponse(
    val id: Long,
    val reminderId: Long?,
    val message: String,
    val status: String)
