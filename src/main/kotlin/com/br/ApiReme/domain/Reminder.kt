
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
    val medication: Medication,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: UserDomain? = null,


    @Column(nullable = false)
    val datetime: LocalDateTime,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: ReminderStatus = ReminderStatus.PENDING,

    @OneToMany(mappedBy = "reminder", cascade = [CascadeType.ALL], orphanRemoval = true)
    val notifications: MutableList<Notification> = mutableListOf()
)

enum class ReminderStatus {
    PENDING, DONE, MISSED
}
