package com.br.ApiReme.controller


import com.br.ApiReme.` Port`.entrada.ReminderPortE
import com.br.ApiReme.Dtos.Reponse.Remider.ReminderDtoResponse
import com.br.ApiReme.Dtos.Request.Remider.ReminderDtoRequest
import com.br.ApiReme.Dtos.Request.Remider.ReminderUpdateDtoRequest
import com.br.ApiReme.domain.Reminder
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/reminders")
@Tag(name = "Reminder", description = "Gerenciamento de Reminder")
class ReminderController(
    private val reminderService: ReminderPortE
) {


    @Operation(summary = "Criar um novo lembrete")
    @ApiResponses(
        ApiResponse(responseCode = "201", description = "Lembrete criado com sucesso"),
        ApiResponse(responseCode = "400", description = "Dados inválidos")
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    fun createReminder(@RequestBody reminder: ReminderDtoRequest): ResponseEntity<ReminderDtoResponse> {
        val created = reminderService.createReminder(reminder)
        return ResponseEntity.status(HttpStatus.CREATED).body(created)
    }




    @Operation(summary = "Buscar lembrete por ID")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Lembrete encontrado"),
        ApiResponse(responseCode = "404", description = "Lembrete não encontrado")
    )
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    fun getReminderById(@PathVariable id: Long): ResponseEntity<ReminderDtoResponse> {
        val reminder = reminderService.findReminderById(id)

            return ResponseEntity.status(HttpStatus.OK).body(reminder)
    }

    @Operation(summary = "Listar todos os lembretes")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    )
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    fun getAllReminders(): ResponseEntity<List<ReminderDtoResponse>> {
        val reminders = reminderService.findAllReminders()
        return ResponseEntity.status(HttpStatus.OK).body(reminders)
    }





    @Operation(summary = "Listar lembretes de uma medicação")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
        ApiResponse(responseCode = "404", description = "Medicação não encontrada")
    )
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/medication/{medicationId}")
    fun getRemindersByMedication(@PathVariable medicationId: Long): ResponseEntity<List<ReminderDtoResponse>> {
        val reminders = reminderService.findRemindersByMedication(medicationId)
        return ResponseEntity.status(HttpStatus.OK).body(reminders)
    }

    @Operation(summary = "Atualizar um lembrete")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Lembrete atualizado com sucesso"),
        ApiResponse(responseCode = "404", description = "Lembrete não encontrado")
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    fun updateReminder(
        @PathVariable id: Long, @RequestBody updatedReminder: ReminderUpdateDtoRequest): ResponseEntity<ReminderDtoResponse> {
        val updated = reminderService.updateReminder(id, updatedReminder)
        return ResponseEntity.status(HttpStatus.OK).body(updated)
    }

    @Operation(summary = "Excluir um lembrete")
    @ApiResponses(
        ApiResponse(responseCode = "204", description = "Lembrete excluído com sucesso"),
        ApiResponse(responseCode = "404", description = "Lembrete não encontrado")
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    fun deleteReminder(@PathVariable id: Long): ResponseEntity<Void> {
        reminderService.deleteReminder(id)
        return ResponseEntity.noContent().build()
    }
}
