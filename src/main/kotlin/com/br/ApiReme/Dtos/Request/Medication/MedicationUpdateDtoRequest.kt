package com.br.ApiReme.Dtos.Request.Medication

data class MedicationUpdateDtoRequest(
    val name: String?,
    val dose: String?,
    val frequency: String?
)
