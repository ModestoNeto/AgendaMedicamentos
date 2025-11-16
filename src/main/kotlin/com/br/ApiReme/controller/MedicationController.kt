package com.br.ApiReme.controller



import com.br.ApiReme.` Port`.entrada.MedicationPortE
import com.br.ApiReme.Dtos.Reponse.Medication.MedicationDtoResponse
import com.br.ApiReme.Dtos.Request.Medication.MedicationDtoRequest
import com.br.ApiReme.Dtos.Request.Medication.MedicationUpdateDtoRequest
import com.br.ApiReme.domain.Medication
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/medications")
class MedicationController(
    private val medicationService: MedicationPortE
) {

    @PostMapping
    fun createMedication(@RequestBody dtoCreateMedicat: MedicationDtoRequest): ResponseEntity<MedicationDtoResponse> {
        val entidadeCreate = medicationService.createMedication(dtoCreateMedicat)
        return ResponseEntity.status(HttpStatus.CREATED).body(entidadeCreate);
    }



    @GetMapping("/{id}")
    fun getMedicationById(@PathVariable id: Long): ResponseEntity<MedicationDtoResponse> {
       val getMedicaID = medicationService.findMedicationById(id)
        return ResponseEntity.status(HttpStatus.CREATED).body(getMedicaID);

    }

    @GetMapping
    fun getAllMedications(): ResponseEntity<List<MedicationDtoResponse>> {
       val lisMedicate = medicationService.findAllMedications()
        return ResponseEntity.status(HttpStatus.CREATED).body(lisMedicate);
    }

    @PutMapping("/{id}")
    fun updateMedication( @RequestBody updatedMedication: MedicationUpdateDtoRequest): ResponseEntity<MedicationDtoResponse> {
        val updateEnti = medicationService.updateMedication(updatedMedication)
        return ResponseEntity.status(HttpStatus.OK).body(updateEnti)
    }


    @DeleteMapping("/{id}")
    fun deleteMedication(@PathVariable id: Long): ResponseEntity<Void>{
        medicationService.deleteMedication(id)
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
