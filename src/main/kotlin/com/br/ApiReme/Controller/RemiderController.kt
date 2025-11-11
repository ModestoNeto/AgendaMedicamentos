package com.br.ApiReme.Controller


import com.br.ApiReme.` Port`.entrada.ReminderPortE
import Reminder
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/reminders")
class ReminderController(
    private val reminderService: ReminderPortE
) {

    // ✅ Criar novo lembrete
    @PostMapping
    fun createReminder(@RequestBody reminder: Reminder): ResponseEntity<Reminder> {
        val created = reminderService.createReminder(reminder)
        return ResponseEntity.status(HttpStatus.CREATED).body(created)
    }

    // ✅ Buscar lembrete por ID
    @GetMapping("/{id}")
    fun getReminderById(@PathVariable id: Long): ResponseEntity<Reminder> {
        val reminder = reminderService.findReminderById(id)
        return if (reminder != null)
            ResponseEntity.ok(reminder)
        else
            ResponseEntity.notFound().build()
    }

    // ✅ Buscar todos os lembretes
    @GetMapping
    fun getAllReminders(): ResponseEntity<List<Reminder>> {
        val reminders = reminderService.findAllReminders()
        return ResponseEntity.ok(reminders)
    }

    // ✅ Buscar lembretes por medicação
    @GetMapping("/medication/{medicationId}")
    fun getRemindersByMedication(@PathVariable medicationId: Long): ResponseEntity<List<Reminder>> {
        val reminders = reminderService.findRemindersByMedication(medicationId)
        return ResponseEntity.ok(reminders)
    }

    // ✅ Atualizar lembrete
    @PutMapping("/{id}")
    fun updateReminder(
        @PathVariable id: Long,
        @RequestBody updatedReminder: Reminder
    ): ResponseEntity<Reminder> {
        val updated = reminderService.updateReminder(id, updatedReminder)
        return ResponseEntity.ok(updated)
    }

    // ✅ Deletar lembrete
    @DeleteMapping("/{id}")
    fun deleteReminder(@PathVariable id: Long): ResponseEntity<Void> {
        reminderService.deleteReminder(id)
        return ResponseEntity.noContent().build()
    }
}
