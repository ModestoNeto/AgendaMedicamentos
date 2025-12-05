package com.br.ApiReme.service


import com.br.ApiReme.` Port`.entrada.ReminderPortE
import com.br.ApiReme.` Port`.saida.ReminderPortS
import com.br.ApiReme.Dtos.Reponse.Remider.ReminderDtoResponse
import com.br.ApiReme.Dtos.Request.Remider.ReminderDtoRequest
import com.br.ApiReme.Dtos.Request.Remider.ReminderUpdateDtoRequest
import com.br.ApiReme.Mapper.ReminderMapper
import com.br.ApiReme.domain.Reminder
import com.br.ApiReme.domain.ReminderStatus

import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ReminderService(
    private val reminderRepository: ReminderPortS,
    private val reminderMapper: ReminderMapper
) : ReminderPortE {

    override fun createReminder(reminder: ReminderDtoRequest): ReminderDtoResponse{
       val createRemideEti = reminderMapper.toEntity(reminder,null,null)
        return reminderMapper.toResponse(reminderRepository.save(createRemideEti))
    }


    override fun findReminderById(id: Long): ReminderDtoResponse{
        val  findReminderEnti = reminderRepository.findById(id)
            ?: throw IllegalArgumentException("Reminder com ID $id não encontrada")
        return reminderMapper.toResponse(findReminderEnti)
    }


    override fun findAllReminders(): List<ReminderDtoResponse>{
        return  reminderRepository.findAll().stream().map { entiRemider -> reminderMapper.toResponse(entiRemider) }.toList()
    }


    override fun findRemindersByMedication(medicationId: Long): List<ReminderDtoResponse> {


         return reminderRepository.findByMedicationId(medicationId).stream().map { entiRemider -> reminderMapper.toResponse(entiRemider) }.toList()
    }

    override fun updateReminder(id: Long, updatedReminder: ReminderUpdateDtoRequest): ReminderDtoResponse {
        val existing = reminderRepository.findById(id)
            ?: throw IllegalArgumentException("Remider com ID $id não encontrado")
        existing.datetime = LocalDateTime.parse(updatedReminder.datetime)
        existing.status = ReminderStatus.valueOf(updatedReminder.status!!.uppercase())

        return reminderMapper.toResponse(reminderRepository.save(existing))
    }

    override fun deleteReminder(id: Long) =
        reminderRepository.deleteById(id)
}
