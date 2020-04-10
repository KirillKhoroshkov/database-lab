package org.polykek.eventsgraph.model

import org.polykek.eventsgraph.type.CITEXT
import java.sql.Timestamp
import javax.persistence.*

@Entity
@Table(name = "account_verification", schema = "events")
class AccountVerification(

        @EmbeddedId
        val accountAsPK: AccountAsPK,

        @Column(name = "full_name", columnDefinition = CITEXT)
        val fullName: String,

        @Column(name = "verified_at")
        val verifiedAt: Timestamp = Timestamp(System.currentTimeMillis())

) : AbstractEntity<AccountAsPK>() {

    override fun getId(): AccountAsPK = accountAsPK
}