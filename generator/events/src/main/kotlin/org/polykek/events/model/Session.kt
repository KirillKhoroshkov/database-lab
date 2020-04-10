package org.polykek.events.model

import org.springframework.data.domain.Persistable
import org.springframework.data.util.ProxyUtils
import java.io.Serializable
import java.sql.Timestamp
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "session", schema = "events")
class Session : Persistable<UUID> {

    /*If one of your entity has an ID field not null,
    Spring will make Hibernate do an update (and so a SELECT before)*/
    /*This is crutch*/
    override fun isNew(): Boolean {
        return true
    }

    override fun getId(): UUID? {
        return accessToken
    }

    @Id
    @Column(name = "access_token")
    var accessToken: UUID? = null

    @Column(name = "refresh_token")
    var refreshToken: UUID? = null

    @Column(name = "expires_in")
    var expiresIn: Timestamp? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    var account: Account? = null

    @Column(name = "created_at")
    var createdAt: Timestamp? = null

    override fun toString() = "Entity of type ${this.javaClass.name} with accessToken: $accessToken"

    override fun equals(other: Any?): Boolean {
        other ?: return false

        if (this === other) return true

        if (javaClass != ProxyUtils.getUserClass(other)) return false

        other as Session

        return if (null == this.accessToken) false else this.accessToken == other.accessToken
    }

    override fun hashCode(): Int {
        return 31
    }
}