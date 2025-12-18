package com.br.ApiReme.service

import com.br.ApiReme.` Port`.saida.MedicationPortS
import com.br.ApiReme.` Port`.saida.UserPortS
import com.br.ApiReme.` Port`.entrada.MedicationPortE
import com.br.ApiReme.Dtos.Reponse.Medication.MedicationDtoResponse
import com.br.ApiReme.Dtos.Request.Medication.MedicationDtoRequest
import com.br.ApiReme.Dtos.Request.Medication.MedicationUpdateDtoRequest
import com.br.ApiReme.Mapper.MedicationMapper
import org.springframework.stereotype.Service

@Service
class MedicationService(
    private val medicationRepository: MedicationPortS,
    private val userRepository: UserPortS,
    private val medicationMapper: MedicationMapper
) : MedicationPortE {

    override fun createMedication(medication: MedicationDtoRequest): MedicationDtoResponse {
        val userId = medication.userId
            ?: throw IllegalArgumentException("userId é obrigatório")

        val user = userRepository.findById(userId)
            ?: throw IllegalArgumentException("Usuário com ID $userId não encontrado")

        val entity = medicationMapper.toEntity(medication, user)
        return medicationMapper.toResponse(medicationRepository.save(entity))
    }

    override fun findMedicationById(idMedication: Long): MedicationDtoResponse {
        val entity = medicationRepository.findById(idMedication)
            ?: throw IllegalArgumentException("Medicação com ID $idMedication não encontrada")
        return medicationMapper.toResponse(entity)
    }

    override fun findAllMedications(): List<MedicationDtoResponse> {
        return medicationRepository.findAll()
            .stream()
            .map { eti -> medicationMapper.toResponse(eti) }
            .toList()
    }

    override fun findMedicationsByUser(userId: Long): List<MedicationDtoResponse> {
        return medicationRepository.findByUserId(userId)
            .stream()
            .map { eti -> medicationMapper.toResponse(eti) }
            .toList()
    }

    override fun updateMedication(updatedMedication: MedicationUpdateDtoRequest): MedicationDtoResponse {
        val existing = medicationRepository.findById(updatedMedication.id)
            ?: throw IllegalArgumentException("Medicação com ID ${updatedMedication.id} não encontrada")

        existing.name = updatedMedication.name
        existing.dose = updatedMedication.dose
        existing.frequency = updatedMedication.frequency

        return medicationMapper.toResponse(medicationRepository.save(existing))
    }

    override fun deleteMedication(id: Long) =
        medicationRepository.deleteById(id)
}
