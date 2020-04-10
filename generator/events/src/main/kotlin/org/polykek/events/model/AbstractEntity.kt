package org.polykek.events.model

import org.springframework.data.domain.Persistable
import org.springframework.data.util.ProxyUtils
import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Id
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class AbstractEntity<ID: Serializable> : Persistable<ID> {

    @Id
    @Column(name = "id")
    var _id: ID? = null

    override fun getId(): ID? {
        return _id
    }

    /*If one of your entity has an ID field not null,
    Spring will make Hibernate do an update (and so a SELECT before)*/
    /*This is crutch*/
    override fun isNew(): Boolean {
        return true
    }

    override fun toString() = "Entity of type ${this.javaClass.name} with id: $_id"

    override fun equals(other: Any?): Boolean {
        other ?: return false

        if (this === other) return true

        if (javaClass != ProxyUtils.getUserClass(other)) return false

        other as AbstractEntity<*>

        return if (null == this._id) false else this._id == other._id
    }

    override fun hashCode(): Int {
        return 31
    }

}