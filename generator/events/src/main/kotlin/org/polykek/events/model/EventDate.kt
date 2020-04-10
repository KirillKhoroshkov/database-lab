package org.polykek.events.model

import java.sql.Timestamp
import javax.persistence.*

@Entity
@Table(name = "event_date", schema = "events")
class EventDate : AbstractEntity<Int>() {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    var event: Event? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    var location: Location? = null

    @Column(name = "description")
    var description: String? = null

    @Column(name = "is_actual")
    var isActual: Boolean? = null

    @Column(name = "is_blocked")
    var isBlocked: Boolean? = null

    @Column(name = "created_at")
    var createdAt: Timestamp? = null

    @Column(name = "start_date")
    var startDate: Timestamp? = null

    @Column(name = "end_date")
    var endDate: Timestamp? = null

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

}