package com.br.ApiReme.Mapper

import com.br.ApiReme.Dtos.Reponse.Medication.MedicationDtoResponse
import com.br.ApiReme.Dtos.Request.Medication.MedicationDtoRequest
import com.br.ApiReme.domain.Medication
import com.br.ApiReme.domain.UserDomain
import org.springframework.stereotype.Component

@Component
object MedicationMapper {

    fun toEntity(dto: MedicationDtoRequest, user: UserDomain): Medication =
        Medication(
            user = user,
            name = dto.name,
            dose = dto.dose,
            frequency = dto.frequency
        )

    fun toResponse(entity: Medication): MedicationDtoResponse =
        MedicationDtoResponse(
            id = entity.id!!,
            name = entity.name!!,
            dose = entity.dose!!,
            frequency = entity.frequency!!,
            userId = entity.user!!.id
        )
}
