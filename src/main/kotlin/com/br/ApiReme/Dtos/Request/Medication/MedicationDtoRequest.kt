package com.br.ApiReme.Dtos.Request.Medication

data class MedicationDtoRequest(
    val userId: Long?,
    val name: String,
    val dose: String,
    val frequency: String)