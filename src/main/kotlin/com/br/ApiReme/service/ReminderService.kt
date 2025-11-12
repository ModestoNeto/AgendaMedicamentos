package com.br.ApiReme.service


import com.br.ApiReme.` Port`.entrada.ReminderPortE
import com.br.ApiReme.` Port`.saida.ReminderPortS
import com.br.ApiReme.domain.Reminder

import org.springframework.stereotype.Service

@Service
class ReminderService(
    private val reminderRepository: ReminderPortS
) : ReminderPortE {

    override fun createReminder(reminder: Reminder): Reminder =
        reminderRepository.save(reminder)

    override fun findReminderById(id: Long): Reminder? =
        reminderRepository.findById(id)

    override fun findAllReminders(): List<Reminder> =
        reminderRepository.findAll()

    override fun findRemindersByMedication(medicationId: Long): List<Reminder> =
        reminderRepository.findByMedicationId(medicationId)

    override fun updateReminder(id: Long, updatedReminder: Reminder): Reminder {
        val existing = reminderRepository.findById(id)
            ?: throw IllegalArgumentException("Lembrete com ID $id não encontrado")

        val newReminder = existing.copy(
            datetime = updatedReminder.datetime,
            status = updatedReminder.status
        )
        return reminderRepository.save(newReminder)
    }

    override fun deleteReminder(id: Long) =
        reminderRepository.deleteById(id)
}
