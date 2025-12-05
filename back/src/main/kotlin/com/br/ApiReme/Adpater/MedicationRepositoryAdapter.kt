package com.br.ApiReme.Adapter

import com.br.ApiReme.` Port`.saida.MedicationPortS
import com.br.ApiReme.domain.Medication
import com.br.ApiReme.repository.MedicationRepository
import org.springframework.stereotype.Component

@Component
class MedicationRepositoryAdapter(
    private val medicationRepository: MedicationRepository
) : MedicationPortS {

    override fun save(medication: Medication): Medication =
        medicationRepository.save(medication)

    override fun findById(id: Long): Medication? =
        medicationRepository.findById(id).orElse(null)

    override fun findByUserId(userId: Long): List<Medication> =
        medicationRepository.findByUserId(userId)

    override fun findAll(): List<Medication> =
        medicationRepository.findAll()

    override fun deleteById(id: Long) =
        medicationRepository.deleteById(id)
}
