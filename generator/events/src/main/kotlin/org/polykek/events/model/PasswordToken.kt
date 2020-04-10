package org.polykek.events.model

import java.sql.Timestamp
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "password_token", schema = "events")
class PasswordToken: AbstractAccountIdEntity() {

    @Column(name = "code")
    var code: UUID? = null

    @Column(name = "expires_in")
    var expiresIn: Timestamp? = null

}