package org.polykek.eventsgraph.model

import java.sql.Timestamp
import javax.persistence.*

@Entity
@Table(name = "event_cost", schema = "events")
class EventCost(

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "event_date_id")
        val eventDate: EventDate,

        @Column(name = "cost")
        val cost: Int,

        @Column(name = "description")
        var description: String,

        @Column(name = "is_actual")
        var isActual: Boolean = true,

        @Column(name = "created_at")
        val createdAt: Timestamp = Timestamp(System.currentTimeMillis())

) : AbstractPersistableEntity<Long>() {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private var _id: Long? = null

    override fun getId(): Long? = _id
}