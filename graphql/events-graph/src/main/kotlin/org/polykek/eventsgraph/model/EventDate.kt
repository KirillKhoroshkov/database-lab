package org.polykek.eventsgraph.model

import java.sql.Timestamp
import javax.persistence.*

@Entity
@Table(name = "event_date", schema = "events")
class EventDate(

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "event_id")
        val event: Event,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "location_id")
        val location: Location,

        @Column(name = "description")
        var description: String,

        @Column(name = "is_actual")
        var isActual: Boolean = true,

        @Column(name = "is_blocked")
        var isBlocked: Boolean = false,

        @Column(name = "start_date")
        val startDate: Timestamp,

        @Column(name = "end_date")
        val endDate: Timestamp,

        @Column(name = "created_at")
        val createdAt: Timestamp = Timestamp(System.currentTimeMillis())

) : AbstractPersistableEntity<Int>() {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private var _id: Int? = null

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "eventDate")
    var eventCosts: MutableSet<EventCost> = mutableSetOf()

    @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinTable(
            schema = "events",
            name = "saved_event_date",
            joinColumns = [JoinColumn(name = "event_date_id")],
            inverseJoinColumns = [JoinColumn(name = "account_id")]
    )
    val savedBy: MutableSet<Account> = mutableSetOf()

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "eventDate")
    val incomingReports: MutableSet<EventDateReport> = mutableSetOf()

    override fun getId(): Int? = _id
}