
package com.br.ApiReme.domain
import com.br.ApiReme.domain.Medication
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "reminders")
data class Reminder(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medication_id", nullable = false)
    var medication: Medication?,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    var user: UserDomain? = null,


    @Column(nullable = false)
    var datetime: LocalDateTime,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: ReminderStatus,

    @OneToMany(mappedBy = "reminder", cascade = [CascadeType.ALL], orphanRemoval = true)
    var notifications: MutableList<Notification> = mutableListOf()
)

enum class ReminderStatus {
    PENDING, DONE, MISSED
}
