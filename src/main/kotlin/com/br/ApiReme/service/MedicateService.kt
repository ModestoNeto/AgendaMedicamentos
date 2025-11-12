package com.br.ApiReme.service


import com.br.ApiReme.` Port`.saida.MedicationPortS
import com.br.ApiReme.` Port`.entrada.MedicationPortE
import com.br.ApiReme.domain.Medication
import org.springframework.stereotype.Service

@Service
class MedicationService(
    private val medicationRepository: MedicationPortS
) : MedicationPortE {

    override fun createMedication(medication: Medication): Medication =
        medicationRepository.save(medication)

    override fun findMedicationById(id: Long): Medication? =
        medicationRepository.findById(id)

    override fun findAllMedications(): List<Medication> =
        medicationRepository.findAll()

    override fun findMedicationsByUser(userId: Long): List<Medication> =
        medicationRepository.findByUserId(userId)

    override fun updateMedication(id: Long, updatedMedication: Medication): Medication {
        val existing = medicationRepository.findById(id)
            ?: throw IllegalArgumentException("Medicação com ID $id não encontrada")

        val newMedication = existing.copy(
            name = updatedMedication.name,
            dose = updatedMedication.dose,
            frequency = updatedMedication.frequency
        )
        return medicationRepository.save(newMedication)
    }

    override fun deleteMedication(id: Long) =
        medicationRepository.deleteById(id)
}
