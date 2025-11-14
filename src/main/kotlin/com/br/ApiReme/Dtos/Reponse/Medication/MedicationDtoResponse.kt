package com.br.ApiReme.Dtos.Reponse.Medication

data class MedicationDtoResponse(
    val id: Long,
    val name: String,
    val dose: String,
    val frequency: String,
    val userId: Long?)
