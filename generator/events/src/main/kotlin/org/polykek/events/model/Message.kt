package org.polykek.events.model

import java.sql.Timestamp
import javax.persistence.*

@Entity
@Table(name = "message", schema = "events")
class Message : AbstractEntity<Int>() {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    var sender: Account? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient_id")
    var recipient: Account? = null

    @Column(name = "message")
    var message: String? = null

    @Column(name = "sent_at")
    var sentAt: Timestamp? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    var event: Event? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_date_id")
    var eventDate: EventDate? = null

}