package com.br.ApiReme.` Port`.entrada

import com.br.ApiReme.Dtos.Reponse.Remider.ReminderDtoResponse
import com.br.ApiReme.Dtos.Request.Remider.ReminderDtoRequest
import com.br.ApiReme.Dtos.Request.Remider.ReminderUpdateDtoRequest
import com.br.ApiReme.domain.Reminder


interface ReminderPortE {
        fun createReminder(reminder: ReminderDtoRequest): ReminderDtoResponse
        fun findReminderById(id: Long): ReminderDtoResponse?
        fun findRemindersByMedication(medicationId: Long): List<ReminderDtoResponse>
        fun updateReminder(id: Long, updatedReminder: ReminderUpdateDtoRequest): ReminderDtoResponse
        fun deleteReminder(id: Long)
        fun findAllReminders(): List<ReminderDtoResponse>

}