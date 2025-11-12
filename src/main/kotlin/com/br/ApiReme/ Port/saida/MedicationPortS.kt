package com.br.ApiReme.` Port`.saida
import com.br.ApiReme.domain.Medication

interface MedicationPortS {
    fun save(medication: Medication): Medication
    fun findById(id: Long): Medication?
    fun findByUserId(userId: Long): List<Medication>
    fun deleteById(id: Long)
    fun findAll(): List<Medication>
}