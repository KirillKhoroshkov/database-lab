package org.polykek.events.model

import java.sql.Timestamp
import javax.persistence.*

@Entity
@Table(name = "event_comment", schema = "events")
class EventComment : AbstractEntity<Int>() {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    var sender: Account? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    var event: Event? = null

    @Column(name = "message")
    var message: String? = null

    @Column(name = "sent_at")
    var sentAt: Timestamp? = null
}