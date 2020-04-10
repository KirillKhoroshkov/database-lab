package org.polykek.eventsgraph.model

import org.springframework.data.domain.Persistable
import java.io.Serializable

abstract class AbstractPersistableEntity<ID: Serializable> : Persistable<ID>, AbstractEntity<ID>() {

    override fun isNew(): Boolean = (id == null)
}