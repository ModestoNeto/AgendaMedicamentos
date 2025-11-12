package com.br.ApiReme.` Port`.entrada

import com.br.ApiReme.domain.Reminder


interface ReminderPortE {
        fun createReminder(reminder: Reminder): Reminder
        fun findReminderById(id: Long): Reminder?
        fun findRemindersByMedication(medicationId: Long): List<Reminder>
        fun updateReminder(id: Long, updatedReminder: Reminder): Reminder
        fun deleteReminder(id: Long)
        fun findAllReminders(): List<Reminder>

}