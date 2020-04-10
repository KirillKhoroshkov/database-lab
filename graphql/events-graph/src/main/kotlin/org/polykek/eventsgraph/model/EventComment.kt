package org.polykek.eventsgraph.model

import java.sql.Timestamp
import javax.persistence.*

@Entity
@Table(name = "event_comment", schema = "events")
class EventComment(

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "sender_id")
        val sender: Account,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "event_id")
        val event: Event,

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