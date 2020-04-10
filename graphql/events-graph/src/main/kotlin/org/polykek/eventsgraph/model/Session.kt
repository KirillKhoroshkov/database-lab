package org.polykek.eventsgraph.model

import java.sql.Timestamp
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "session", schema = "events")
class Session(

        @Id
        @Column(name = "access_token")
        val accessToken: UUID,

        @Column(name = "refresh_token")
        val refreshToken: UUID,

        @Column(name = "expires_in")
        val expiresIn: Timestamp,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "account_id")
        val account: Account,

        @Column(name = "created_at")
        val createdAt: Timestamp = Timestamp(System.currentTimeMillis())

) : AbstractEntity<UUID>() {

        override fun getId(): UUID = accessToken
}