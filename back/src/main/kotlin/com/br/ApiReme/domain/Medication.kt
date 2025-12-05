package com.br.ApiReme.domain
import jakarta.persistence.*

@Entity
@Table(name = "medications")
data class Medication(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = true)
    var user: UserDomain? = null,

    @Column(nullable = false)
    var name: String?,

    @Column(nullable = false)
    var dose: String?,

    @Column(nullable = false)
    var frequency: String?,

    @OneToMany(
        mappedBy = "medication",
        cascade = [CascadeType.ALL],
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    var reminders: MutableList<Reminder> = mutableListOf()
)
