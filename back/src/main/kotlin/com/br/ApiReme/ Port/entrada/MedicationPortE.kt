
package com.br.ApiReme.` Port`.entrada
import  com.br.ApiReme.Dtos.Reponse.Medication.MedicationDtoResponse
import com.br.ApiReme.Dtos.Request.Medication.MedicationDtoRequest
import com.br.ApiReme.Dtos.Request.Medication.MedicationUpdateDtoRequest
import com.br.ApiReme.domain.Medication


interface MedicationPortE {
    fun createMedication(medication: MedicationDtoRequest): MedicationDtoResponse
    fun findMedicationById(id: Long): MedicationDtoResponse?
    fun findMedicationsByUser(userId: Long): List<MedicationDtoResponse>
    fun updateMedication(updatedMedication: MedicationUpdateDtoRequest):  MedicationDtoResponse
    fun deleteMedication(id: Long)
    fun findAllMedications(): List<MedicationDtoResponse>
}