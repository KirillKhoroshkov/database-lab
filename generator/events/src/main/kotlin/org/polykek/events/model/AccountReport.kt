package org.polykek.events.model

import java.sql.Timestamp
import javax.persistence.*

@Entity
@Table(name = "account_report", schema = "events")
class AccountReport : AbstractEntity<Int>() {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    var account: Account? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    var sender: Account? = null

    @Column(name = "message")
    var message: String? = null

    @Column(name = "sent_at")
    var sentAt: Timestamp? = null

}