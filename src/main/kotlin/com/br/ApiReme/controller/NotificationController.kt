package com.br.ApiReme.controller




import com.br.ApiReme.` Port`.entrada.NotificatePortE
import com.br.ApiReme.Dtos.Reponse.Notification.NotificationDtoResponse
import com.br.ApiReme.Dtos.Request.Notification.NotificationDtoRequest
import com.br.ApiReme.Dtos.Request.Notification.NotificationUpdateDtoRequest
import com.br.ApiReme.domain.Notification
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/notifications")
@Tag(name = "Notification", description = "Gerenciamento de Notification")
class NotificationController(
    private val notificationService: NotificatePortE
) {

    @Operation(summary = "Criar uma nova notificação")
    @ApiResponses(
        ApiResponse(responseCode = "201", description = "Notificação criada com sucesso"),
        ApiResponse(responseCode = "400", description = "Dados inválidos")
    )

    @PostMapping
    fun createNotification(@RequestBody notification: NotificationDtoRequest): ResponseEntity<NotificationDtoResponse> {
       val createNotification = notificationService.createNotification(notification)
        return ResponseEntity.status(HttpStatus.CREATED).body(createNotification)
    }


    @Operation(summary = "Buscar notificação por ID")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Notificação encontrada"),
        ApiResponse(responseCode = "404", description = "Notificação não encontrada")
    )

    @GetMapping("/{id}")
    fun getNotificationById(@PathVariable id: Long): ResponseEntity<NotificationDtoResponse>{
        val getNotificationByIdEnti =notificationService.findNotificationById(id)
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(getNotificationByIdEnti)
    }


    @Operation(summary = "Listar notificações por lembrete")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
        ApiResponse(responseCode = "404", description = "Lembrete não encontrado")
    )
    @GetMapping("/reminder/{reminderId}")
    fun getByReminder(@PathVariable reminderId: Long): ResponseEntity<List<NotificationDtoResponse>> {
        val listNotificationRemider = notificationService.findNotificationsByReminder(reminderId)
        return ResponseEntity.status(HttpStatus.OK).body(listNotificationRemider)
    }


    @Operation(summary = "Listar todas as notificações")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    )
    @GetMapping
    fun getAllNotifications():  ResponseEntity<List<NotificationDtoResponse>>{

        val listNotificate = notificationService.findAllNotifications()
        return ResponseEntity.status(HttpStatus.OK).body(listNotificate)
    }
    @Operation(summary = "Atualizar uma notificação")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Notificação atualizada com sucesso"),
        ApiResponse(responseCode = "404", description = "Notificação não encontrada")
    )
    @PutMapping("/{id}")
    fun updateNotification(@PathVariable id: Long, @RequestBody updatedNotification: NotificationUpdateDtoRequest): ResponseEntity<NotificationDtoResponse> {
       val notificateUpdateEnti = notificationService.updateNotification(id, updatedNotification)
        return ResponseEntity.status(HttpStatus.OK).body(notificateUpdateEnti)

    }
    @Operation(summary = "Excluir uma notificação")
    @ApiResponses(
        ApiResponse(responseCode = "204", description = "Notificação excluída com sucesso"),
        ApiResponse(responseCode = "404", description = "Notificação não encontrada")
    )
    @DeleteMapping("/{id}")
    fun deleteNotification(@PathVariable id: Long): ResponseEntity<Void>{
        notificationService.deleteNotification(id)
        return ResponseEntity.status(HttpStatus.OK).build()
    }
}
