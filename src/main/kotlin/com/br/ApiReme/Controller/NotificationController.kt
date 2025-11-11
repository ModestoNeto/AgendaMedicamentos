package com.br.ApiReme.Controller



import Notification
import com.br.ApiReme.` Port`.entrada.NotificatePortE
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/notifications")
class NotificationController(
    private val notificationService: NotificatePortE
) {

    @PostMapping
    fun createNotification(@RequestBody notification: Notification): Notification =
        notificationService.createNotification(notification)

    @GetMapping("/{id}")
    fun getNotificationById(@PathVariable id: Long): Notification? =
        notificationService.findNotificationById(id)

    @GetMapping("/reminder/{reminderId}")
    fun getByReminder(@PathVariable reminderId: Long): List<Notification> =
        notificationService.findNotificationsByReminder(reminderId)

    @GetMapping
    fun getAllNotifications(): List<Notification> =
        notificationService.findAllNotifications()

    @PutMapping("/{id}")
    fun updateNotification(
        @PathVariable id: Long,
        @RequestBody updatedNotification: Notification
    ): Notification =
        notificationService.updateNotification(id, updatedNotification)

    @DeleteMapping("/{id}")
    fun deleteNotification(@PathVariable id: Long) {
        notificationService.deleteNotification(id)
    }
}
