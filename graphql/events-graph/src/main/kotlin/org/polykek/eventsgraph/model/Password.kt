package org.polykek.eventsgraph.model

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "password", schema = "events")
class Password(

        @EmbeddedId
        val accountAsPK: AccountAsPK,

        @Column(name = "salt")
        var salt: UUID,

        @Column(name = "hash")
        var hash: ByteArray

) : AbstractEntity<AccountAsPK>() {

    override fun getId(): AccountAsPK = accountAsPK
}