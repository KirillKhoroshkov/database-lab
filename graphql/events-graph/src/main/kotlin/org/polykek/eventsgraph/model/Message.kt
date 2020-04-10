package org.polykek.eventsgraph.model

import java.sql.Timestamp
import javax.persistence.*

@Entity
@Table(name = "message", schema = "events")
class Message(

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "sender_id")
        val sender: Account,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "recipient_id")
        val recipient: Account,

        @Column(name = "message")
        val message: String,

        @Column(name = "sent_at")
        val sentAt: Timestamp = Timestamp(System.currentTimeMillis()),

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "event_id")
        val event: Event? = null,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "event_date_id")
        val eventDate: EventDate? = null

) : AbstractPersistableEntity<Int>() {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private var _id: Int? = null

    override fun getId(): Int? = _id
}