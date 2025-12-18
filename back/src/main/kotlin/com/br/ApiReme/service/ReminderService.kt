package com.br.ApiReme.service

import com.br.ApiReme.` Port`.entrada.ReminderPortE
import com.br.ApiReme.` Port`.saida.ReminderPortS
import com.br.ApiReme.` Port`.saida.MedicationPortS
import com.br.ApiReme.` Port`.saida.UserPortS
import com.br.ApiReme.Dtos.Reponse.Remider.ReminderDtoResponse
import com.br.ApiReme.Dtos.Request.Remider.ReminderDtoRequest
import com.br.ApiReme.Dtos.Request.Remider.ReminderUpdateDtoRequest
import com.br.ApiReme.Mapper.ReminderMapper
import com.br.ApiReme.domain.ReminderStatus
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ReminderService(
    private val reminderRepository: ReminderPortS,
    private val medicationRepository: MedicationPortS,
    private val userRepository: UserPortS,
    private val reminderMapper: ReminderMapper
) : ReminderPortE {

    override fun createReminder(reminder: ReminderDtoRequest): ReminderDtoResponse {
        val medicationId = reminder.medicationId
            ?: throw IllegalArgumentException("medicationId é obrigatório")
        val userId = reminder.userId
            ?: throw IllegalArgumentException("userId é obrigatório")

        val medication = medicationRepository.findById(medicationId)
            ?: throw IllegalArgumentException("Medicação com ID $medicationId não encontrada")

        val user = userRepository.findById(userId)
            ?: throw IllegalArgumentException("Usuário com ID $userId não encontrado")

        val entity = reminderMapper.toEntity(reminder, medication, user)
        return reminderMapper.toResponse(reminderRepository.save(entity))
    }

    override fun findReminderById(id: Long): ReminderDtoResponse {
        val findReminderEnti = reminderRepository.findById(id)
            ?: throw IllegalArgumentException("Reminder com ID $id não encontrada")
        return reminderMapper.toResponse(findReminderEnti)
    }

    override fun findAllReminders(): List<ReminderDtoResponse> {
        return reminderRepository.findAll()
            .stream()
            .map { enti -> reminderMapper.toResponse(enti) }
            .toList()
    }

    override fun findRemindersByMedication(medicationId: Long): List<ReminderDtoResponse> {
        return reminderRepository.findByMedicationId(medicationId)
            .stream()
            .map { enti -> reminderMapper.toResponse(enti) }
            .toList()
    }

    override fun updateReminder(id: Long, updatedReminder: ReminderUpdateDtoRequest): ReminderDtoResponse {
        val existing = reminderRepository.findById(id)
            ?: throw IllegalArgumentException("Remider com ID $id não encontrado")

        if (!updatedReminder.datetime.isNullOrBlank()) {
            existing.datetime = LocalDateTime.parse(updatedReminder.datetime)
        }
        updatedReminder.status?.let {
            existing.status = ReminderStatus.valueOf(it.uppercase())
        }

        return reminderMapper.toResponse(reminderRepository.save(existing))
    }

    override fun deleteReminder(id: Long) =
        reminderRepository.deleteById(id)
}
