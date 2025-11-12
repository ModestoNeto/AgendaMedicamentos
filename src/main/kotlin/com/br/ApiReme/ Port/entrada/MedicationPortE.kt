
package com.br.ApiReme.` Port`.entrada
import com.br.ApiReme.domain.Medication


interface MedicationPortE {
    fun createMedication(medication: Medication): Medication
    fun findMedicationById(id: Long): Medication?
    fun findMedicationsByUser(userId: Long): List<Medication>
    fun updateMedication(id: Long, updatedMedication: Medication): Medication
    fun deleteMedication(id: Long)
    fun findAllMedications(): List<Medication>
}