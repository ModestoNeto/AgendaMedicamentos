package com.br.ApiReme.controller


import com.br.ApiReme.` Port`.entrada.ReminderPortE
import com.br.ApiReme.Dtos.Reponse.Remider.ReminderDtoResponse
import com.br.ApiReme.Dtos.Request.Remider.ReminderDtoRequest
import com.br.ApiReme.Dtos.Request.Remider.ReminderUpdateDtoRequest
import com.br.ApiReme.domain.Reminder
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/reminders")
class ReminderController(
    private val reminderService: ReminderPortE
) {


    @PostMapping
    fun createReminder(@RequestBody reminder: ReminderDtoRequest): ResponseEntity<ReminderDtoResponse> {
        val created = reminderService.createReminder(reminder)
        return ResponseEntity.status(HttpStatus.CREATED).body(created)
    }


    @GetMapping("/{id}")
    fun getReminderById(@PathVariable id: Long): ResponseEntity<ReminderDtoResponse> {
        val reminder = reminderService.findReminderById(id)

            return ResponseEntity.status(HttpStatus.OK).body(reminder)
    }


    @GetMapping
    fun getAllReminders(): ResponseEntity<List<ReminderDtoResponse>> {
        val reminders = reminderService.findAllReminders()
        return ResponseEntity.status(HttpStatus.OK).body(reminders)
    }


    @GetMapping("/medication/{medicationId}")
    fun getRemindersByMedication(@PathVariable medicationId: Long): ResponseEntity<List<ReminderDtoResponse>> {
        val reminders = reminderService.findRemindersByMedication(medicationId)
        return ResponseEntity.status(HttpStatus.OK).body(reminders)
    }

    @PutMapping("/{id}")
    fun updateReminder(
        @PathVariable id: Long, @RequestBody updatedReminder: ReminderUpdateDtoRequest): ResponseEntity<ReminderDtoResponse> {
        val updated = reminderService.updateReminder(id, updatedReminder)
        return ResponseEntity.status(HttpStatus.OK).body(updated)
    }


    @DeleteMapping("/{id}")
    fun deleteReminder(@PathVariable id: Long): ResponseEntity<Void> {
        reminderService.deleteReminder(id)
        return ResponseEntity.noContent().build()
    }
}
