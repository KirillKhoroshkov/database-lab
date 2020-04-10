package org.polykek.events.model

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "password", schema = "events")
class Password: AbstractAccountIdEntity() {

    @Column(name = "salt")
    var salt: UUID? = null

    @Column(name = "hash")
    var hash: ByteArray? = null
}