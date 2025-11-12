package com.br.ApiReme.domain
import jakarta.persistence.*

@Entity
@Table(name = "medications")
data class Medication(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = true)
    val user: UserDomain? = null,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val dose: String,

    @Column(nullable = false)
    val frequency: String,

    @OneToMany(
        mappedBy = "medication",
        cascade = [CascadeType.ALL],
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    val reminders: MutableList<Reminder> = mutableListOf()
)
