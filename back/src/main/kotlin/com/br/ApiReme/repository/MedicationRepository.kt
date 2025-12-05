package com.br.ApiReme.repository


import com.br.ApiReme.domain.Medication
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MedicationRepository : JpaRepository<Medication, Long> {
    fun findByUserId(userId: Long): List<Medication>
}
