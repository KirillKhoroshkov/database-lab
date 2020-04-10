package org.polykek.events.model

import java.sql.Timestamp
import javax.persistence.*

@Entity
@Table(name = "event_cost", schema = "events")
class EventCost : AbstractEntity<Long>() {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_date_id")
    var eventDate: EventDate? = null

    @Column(name = "cost")
    var cost: Int? = null

    @Column(name = "description")
    var description: String? = null

    @Column(name = "is_actual")
    var isActual: Boolean? = null

    @Column(name = "created_at")
    var createdAt: Timestamp? = null
}