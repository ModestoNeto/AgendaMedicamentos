package com.br.ApiReme.Dtos.Request.Remider

data class ReminderDtoRequest(
    val medicationId: Long,
    val userId: Long?,
    val datetime: String,
    val status: String
)