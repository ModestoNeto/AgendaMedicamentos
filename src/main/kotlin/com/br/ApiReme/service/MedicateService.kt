package com.br.ApiReme.service


import com.br.ApiReme.` Port`.saida.MedicationPortS
import com.br.ApiReme.` Port`.entrada.MedicationPortE
import com.br.ApiReme.Dtos.Reponse.Medication.MedicationDtoResponse
import com.br.ApiReme.Dtos.Request.Medication.MedicationDtoRequest
import com.br.ApiReme.Dtos.Request.Medication.MedicationUpdateDtoRequest
import com.br.ApiReme.Mapper.MedicationMapper

import org.springframework.stereotype.Service


@Service
class MedicationService(

    private val medicationRepository: MedicationPortS,

    private val medicationMapper: MedicationMapper



) : MedicationPortE {

    override fun createMedication(medication: MedicationDtoRequest): MedicationDtoResponse {
        val entidadeMedication = medicationMapper.toEntity(medication,null)

        return medicationMapper.toResponse(medicationRepository.save(entidadeMedication))
    }


    override fun findMedicationById(idMedication: Long): MedicationDtoResponse{
        var entididadeMedication = medicationRepository.findById(idMedication) ?:
        throw IllegalArgumentException("Medicação com ID ${idMedication} não encontrada")

        return medicationMapper.toResponse(entididadeMedication)
    }



    override fun findAllMedications(): List<MedicationDtoResponse> {
        return  medicationRepository.findAll().stream().map {eti -> medicationMapper.toResponse(eti)}.toList()
    }


    override fun findMedicationsByUser(userId: Long): List<MedicationDtoResponse> {
       return medicationRepository.findByUserId(userId).stream().map { etiUser -> medicationMapper.toResponse(etiUser) }.toList()
    }


    override fun updateMedication(updatedMedication: MedicationUpdateDtoRequest): MedicationDtoResponse {
        var existingMedica = medicationRepository.findById(updatedMedication.id)
            ?: throw IllegalArgumentException("Medicação com ID ${updatedMedication.id} não encontrada")

        existingMedica.name = updatedMedication.name
        existingMedica.dose = updatedMedication.dose
        existingMedica.frequency = updatedMedication.frequency


        return medicationMapper.toResponse(medicationRepository.save(existingMedica))
    }

    override fun deleteMedication(id: Long) =
        medicationRepository.deleteById(id)
}


