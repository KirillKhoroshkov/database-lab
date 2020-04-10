package org.polykek.eventsgraph.model

import org.springframework.data.util.ProxyUtils
import java.io.Serializable

abstract class AbstractEntity<ID: Serializable> : Serializable {

    override fun equals(other: Any?): Boolean {
        other ?: return false
        if (this === other) return true
        if (javaClass != ProxyUtils.getUserClass(other)) return false
        other as AbstractEntity<*>
        return if (null == this.getId()) false else this.getId() == other.getId()
    }

    override fun hashCode(): Int = 31

    override fun toString(): String = "${this.javaClass.name}(id=${getId()})"

    abstract fun getId(): ID?
}