package org.polykek.events.model

import org.polykek.events.type.CITEXT
import java.sql.Timestamp
import javax.persistence.*

@Entity
@Table(name = "account_verification", schema = "events")
class AccountVerification: AbstractAccountIdEntity() {

    @Column(name = "full_name", columnDefinition = CITEXT)
    var fullName: String? = null

    @Column(name = "verified_at")
    var verifiedAt: Timestamp? = null

}