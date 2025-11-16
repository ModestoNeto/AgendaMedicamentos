package com.br.ApiReme.domain
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "notifications")
data class Notification(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reminder_id", nullable = true)
    var reminder: Reminder? = null,

    @Column(name = "sent_at")
    var sentAt: LocalDateTime? = null,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: NotificationStatus,

    var messagem: String?


)

enum class NotificationStatus {
    PENDING, SENT, FAILED
}
