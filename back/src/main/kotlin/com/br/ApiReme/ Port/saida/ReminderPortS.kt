package com.br.ApiReme.` Port`.saida

import com.br.ApiReme.domain.Reminder

interface ReminderPortS {
    fun save(reminder: Reminder): Reminder
    fun findById(id: Long): Reminder?
    fun findByMedicationId(medicationId: Long): List<Reminder>
    fun deleteById(id: Long)
    fun findAll(): List<Reminder>
    fun findByUserId(userId: Long): List<Reminder>
}