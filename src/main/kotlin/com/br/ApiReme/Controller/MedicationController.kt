package com.br.ApiReme.Controller



import Medication
import com.br.ApiReme.` Port`.entrada.MedicationPortE
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/medications")
class MedicationController(
    private val medicationService: MedicationPortE
) {

    @PostMapping
    fun createMedication(@RequestBody medication: Medication): Medication =
        medicationService.createMedication(medication)

    @GetMapping("/{id}")
    fun getMedicationById(@PathVariable id: Long): Medication? =
        medicationService.findMedicationById(id)

    @GetMapping
    fun getAllMedications(): List<Medication> =
        medicationService.findAllMedications()

    @PutMapping("/{id}")
    fun updateMedication(
        @PathVariable id: Long,
        @RequestBody updatedMedication: Medication
    ): Medication =
        medicationService.updateMedication(id, updatedMedication)

    @DeleteMapping("/{id}")
    fun deleteMedication(@PathVariable id: Long) {
        medicationService.deleteMedication(id)
    }
}
