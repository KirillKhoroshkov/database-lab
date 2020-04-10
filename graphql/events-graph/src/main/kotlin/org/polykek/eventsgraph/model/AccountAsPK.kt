package org.polykek.eventsgraph.model

import java.io.Serializable
import javax.persistence.Embeddable
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.OneToOne

@Embeddable
data class AccountAsPK(

        @OneToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "account_id", referencedColumnName = "id")
        val account: Account

) : Serializable