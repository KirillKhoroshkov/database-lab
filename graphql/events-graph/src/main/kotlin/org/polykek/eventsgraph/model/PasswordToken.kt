package org.polykek.eventsgraph.model

import java.sql.Timestamp
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "password_token", schema = "events")
class PasswordToken(

        @EmbeddedId
        val accountAsPK: AccountAsPK,

        @Column(name = "code")
        val code: UUID,

        @Column(name = "expires_in")
        val expiresIn: Timestamp

) : AbstractEntity<AccountAsPK>() {

    override fun getId(): AccountAsPK = accountAsPK
}