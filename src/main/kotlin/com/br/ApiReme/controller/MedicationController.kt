package com.br.ApiReme.controller



import com.br.ApiReme.` Port`.entrada.MedicationPortE
import com.br.ApiReme.Dtos.Reponse.Medication.MedicationDtoResponse
import com.br.ApiReme.Dtos.Request.Medication.MedicationDtoRequest
import com.br.ApiReme.Dtos.Request.Medication.MedicationUpdateDtoRequest
import com.br.ApiReme.domain.Medication
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/medications")
@Tag(name = "Medications", description = "Gerenciamento de Medicações")
class MedicationController(
    private val medicationService: MedicationPortE
) {

    @Operation(summary = "Criar uma nova medicação")
    @ApiResponses(
        ApiResponse(responseCode = "201", description = "Medicação criada com sucesso"),
        ApiResponse(responseCode = "400", description = "Dados inválidos")
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    fun createMedication(@RequestBody dtoCreateMedicat: MedicationDtoRequest): ResponseEntity<MedicationDtoResponse> {
        val entidadeCreate = medicationService.createMedication(dtoCreateMedicat)
        return ResponseEntity.status(HttpStatus.CREATED).body(entidadeCreate);
    }


    @Operation(summary = "Buscar medicação por ID")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Medicação encontrada"),
        ApiResponse(responseCode = "404", description = "Medicação não encontrada")
    )
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    fun getMedicationById(@PathVariable id: Long): ResponseEntity<MedicationDtoResponse> {
       val getMedicaID = medicationService.findMedicationById(id)
        return ResponseEntity.status(HttpStatus.CREATED).body(getMedicaID);

    }


    @Operation(summary = "Listar todas as medicações")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    )
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    fun getAllMedications(): ResponseEntity<List<MedicationDtoResponse>> {
       val lisMedicate = medicationService.findAllMedications()
        return ResponseEntity.status(HttpStatus.CREATED).body(lisMedicate);
    }

    @Operation(summary = "Atualizar uma medicação")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Medicação atualizada com sucesso"),
        ApiResponse(responseCode = "404", description = "Medicação não encontrada")
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    fun updateMedication( @RequestBody updatedMedication: MedicationUpdateDtoRequest): ResponseEntity<MedicationDtoResponse> {
        val updateEnti = medicationService.updateMedication(updatedMedication)
        return ResponseEntity.status(HttpStatus.OK).body(updateEnti)
    }

    @Operation(summary = "Excluir uma medicação")
    @ApiResponses(
        ApiResponse(responseCode = "204", description = "Medicação excluída com sucesso"),
        ApiResponse(responseCode = "404", description = "Medicação não encontrada")
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    fun deleteMedication(@PathVariable id: Long): ResponseEntity<Void>{
        medicationService.deleteMedication(id)
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
