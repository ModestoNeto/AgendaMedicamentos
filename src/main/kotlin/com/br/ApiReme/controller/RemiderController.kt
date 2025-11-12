package com.br.ApiReme.controller


import com.br.ApiReme.` Port`.entrada.ReminderPortE
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
    fun createReminder(@RequestBody reminder: Reminder): ResponseEntity<Reminder> {
        val created = reminderService.createReminder(reminder)
        return ResponseEntity.status(HttpStatus.CREATED).body(created)
    }


    @GetMapping("/{id}")
    fun getReminderById(@PathVariable id: Long): ResponseEntity<Reminder> {
        val reminder = reminderService.findReminderById(id)
        return if (reminder != null)
            ResponseEntity.ok(reminder)
        else
            ResponseEntity.notFound().build()
    }


    @GetMapping
    fun getAllReminders(): ResponseEntity<List<Reminder>> {
        val reminders = reminderService.findAllReminders()
        return ResponseEntity.ok(reminders)
    }


    @GetMapping("/medication/{medicationId}")
    fun getRemindersByMedication(@PathVariable medicationId: Long): ResponseEntity<List<Reminder>> {
        val reminders = reminderService.findRemindersByMedication(medicationId)
        return ResponseEntity.ok(reminders)
    }

    @PutMapping("/{id}")
    fun updateReminder(
        @PathVariable id: Long,
        @RequestBody updatedReminder: Reminder
    ): ResponseEntity<Reminder> {
        val updated = reminderService.updateReminder(id, updatedReminder)
        return ResponseEntity.ok(updated)
    }


    @DeleteMapping("/{id}")
    fun deleteReminder(@PathVariable id: Long): ResponseEntity<Void> {
        reminderService.deleteReminder(id)
        return ResponseEntity.noContent().build()
    }
}
