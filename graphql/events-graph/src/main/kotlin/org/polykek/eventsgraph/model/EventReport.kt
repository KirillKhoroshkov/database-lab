package org.polykek.eventsgraph.model

import java.sql.Timestamp
import javax.persistence.*

@Entity
@Table(name = "event_report", schema = "events")
class EventReport(

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "event_id")
        val event: Event,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "sender_id")
        val sender: Account,

        @Column(name = "message")
        val message: String,

        @Column(name = "sent_at")
        val sentAt: Timestamp = Timestamp(System.currentTimeMillis())

) : AbstractPersistableEntity<Int>() {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private var _id: Int? = null

    override fun getId(): Int? = _id
}