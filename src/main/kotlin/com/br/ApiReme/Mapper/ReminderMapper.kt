package com.br.ApiReme.Mapper

import com.br.ApiReme.Dtos.Reponse.Remider.ReminderDtoResponse
import com.br.ApiReme.Dtos.Request.Remider.ReminderDtoRequest
import com.br.ApiReme.domain.Medication
import com.br.ApiReme.domain.Reminder
import com.br.ApiReme.domain.ReminderStatus
import com.br.ApiReme.domain.UserDomain
import java.time.LocalDateTime
object ReminderMapper {
    fun toEntity(
        dto: ReminderDtoRequest,
        medication: Medication?,
        user: UserDomain?
    ): Reminder =
        Reminder(
            medication = medication,
            datetime = LocalDateTime.parse(dto.datetime),
            status = enumValueOf<ReminderStatus>(dto.status.uppercase()),
            user = user
        )


    fun toResponse(entity: Reminder): ReminderDtoResponse =
        ReminderDtoResponse(
            id = entity.id,
            medicationId = entity.medication!!.id,
            userId = entity.user?.id,
            datetime = entity.datetime.toString(),
            status = entity.status.name
        )
}