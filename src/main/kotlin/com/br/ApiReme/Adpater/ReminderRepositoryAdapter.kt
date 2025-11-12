package com.br.ApiReme.Adapter

import com.br.ApiReme.` Port`.saida.ReminderPortS
import com.br.ApiReme.domain.Reminder
import com.br.ApiReme.repository.ReminderRepository
import org.springframework.stereotype.Component

@Component
class ReminderRepositoryAdapter(
    private val reminderRepository: ReminderRepository
) : ReminderPortS {

    override fun save(reminder: Reminder): Reminder =
        reminderRepository.save(reminder)

    override fun findById(id: Long): Reminder? =
        reminderRepository.findById(id).orElse(null)

    override fun findByUserId(userId: Long): List<Reminder> =
        reminderRepository.findByUserId(userId)

    override fun findAll(): List<Reminder> =
        reminderRepository.findAll()

    override fun deleteById(id: Long) =
        reminderRepository.deleteById(id)
    override fun findByMedicationId(medicationId: Long): List<Reminder> =
        reminderRepository.findByMedicationId(medicationId)
}
