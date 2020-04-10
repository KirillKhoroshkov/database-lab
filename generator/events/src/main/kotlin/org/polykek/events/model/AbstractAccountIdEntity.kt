package org.polykek.events.model

import org.springframework.data.domain.Persistable
import org.springframework.data.util.ProxyUtils
import java.io.Serializable
import javax.persistence.*

@MappedSuperclass
abstract class AbstractAccountIdEntity : Persistable<AccountAsId> {

    @EmbeddedId
    var pk: AccountAsId? = null

    /*If one of your entity has an ID field not null,
    Spring will make Hibernate do an update (and so a SELECT before)*/
    /*This is crutch*/
    override fun isNew(): Boolean {
        return true
    }

    override fun getId(): AccountAsId? {
        return pk
    }

    override fun toString() = "Entity of type ${this.javaClass.name} with primary key: $pk"

    override fun equals(other: Any?): Boolean {
        other ?: return false

        if (this === other) return true

        if (javaClass != ProxyUtils.getUserClass(other)) return false

        other as AbstractAccountIdEntity

        return if (null == this.pk) false else this.pk == other.pk
    }

    override fun hashCode(): Int {
        return 31
    }

}

@Embeddable
class AccountAsId : Serializable {

    constructor(account: Account) {
        this.account = account
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    var account: Account? = null

    override fun toString() = "${this.javaClass.name} with account: $account"

    override fun equals(other: Any?): Boolean {
        other ?: return false

        if (this === other) return true

        if (javaClass != ProxyUtils.getUserClass(other)) return false

        other as AccountAsId

        return if (null == this.account) false else this.account == other.account
    }

    override fun hashCode(): Int {
        return 31
    }
}
