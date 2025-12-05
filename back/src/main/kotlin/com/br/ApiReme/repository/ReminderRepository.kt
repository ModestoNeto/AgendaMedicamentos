package com.br.ApiReme.repository


import com.br.ApiReme.domain.Reminder
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ReminderRepository : JpaRepository<Reminder, Long> {
    fun findByUserId(userId: Long): List<Reminder>
    fun findByMedicationId(medicationId: Long): List<Reminder>
}
