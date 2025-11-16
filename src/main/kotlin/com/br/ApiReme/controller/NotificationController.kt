package com.br.ApiReme.controller




import com.br.ApiReme.` Port`.entrada.NotificatePortE
import com.br.ApiReme.Dtos.Reponse.Notification.NotificationDtoResponse
import com.br.ApiReme.Dtos.Request.Notification.NotificationDtoRequest
import com.br.ApiReme.Dtos.Request.Notification.NotificationUpdateDtoRequest
import com.br.ApiReme.domain.Notification
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/notifications")
class NotificationController(
    private val notificationService: NotificatePortE
) {

    @PostMapping
    fun createNotification(@RequestBody notification: NotificationDtoRequest): ResponseEntity<NotificationDtoResponse> {
       val createNotification = notificationService.createNotification(notification)
        return ResponseEntity.status(HttpStatus.CREATED).body(createNotification)
    }

    @GetMapping("/{id}")
    fun getNotificationById(@PathVariable id: Long): ResponseEntity<NotificationDtoResponse>{
        val getNotificationByIdEnti =notificationService.findNotificationById(id)
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(getNotificationByIdEnti)
    }


    @GetMapping("/reminder/{reminderId}")
    fun getByReminder(@PathVariable reminderId: Long): ResponseEntity<List<NotificationDtoResponse>> {
        val listNotificationRemider = notificationService.findNotificationsByReminder(reminderId)
        return ResponseEntity.status(HttpStatus.OK).body(listNotificationRemider)
    }


    @GetMapping
    fun getAllNotifications():  ResponseEntity<List<NotificationDtoResponse>>{

        val listNotificate = notificationService.findAllNotifications()
        return ResponseEntity.status(HttpStatus.OK).body(listNotificate)
    }

    @PutMapping("/{id}")
    fun updateNotification(@PathVariable id: Long, @RequestBody updatedNotification: NotificationUpdateDtoRequest): ResponseEntity<NotificationDtoResponse> {
       val notificateUpdateEnti = notificationService.updateNotification(id, updatedNotification)
        return ResponseEntity.status(HttpStatus.OK).body(notificateUpdateEnti)

    }

    @DeleteMapping("/{id}")
    fun deleteNotification(@PathVariable id: Long): ResponseEntity<Void>{
        notificationService.deleteNotification(id)
        return ResponseEntity.status(HttpStatus.OK).build()
    }
}
