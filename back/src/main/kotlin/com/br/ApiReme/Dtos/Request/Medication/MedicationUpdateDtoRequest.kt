package com.br.ApiReme.Dtos.Request.Medication

data class MedicationUpdateDtoRequest(
    val id : Long,
    val name: String?,
    val dose: String?,
    val frequency: String?
)
