package com.br.ApiReme.Dtos.Reponse.Remider

data class ReminderDtoResponse(
    val id: Long,
    val medicationId: Long,
    val userId: Long?,
    val datetime: String,
    val status: String
)
