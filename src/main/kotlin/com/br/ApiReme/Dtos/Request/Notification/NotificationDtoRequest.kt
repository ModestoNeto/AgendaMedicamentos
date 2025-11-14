package com.br.ApiReme.Dtos.Request.Notification

data class NotificationDtoRequest(
    val reminderId: Long,
    val message: String,
    val status: String)
