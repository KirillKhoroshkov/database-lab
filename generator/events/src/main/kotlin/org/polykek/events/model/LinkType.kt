package org.polykek.events.model

import org.polykek.events.type.CITEXT
import javax.persistence.*

@Entity
@Table(name = "link_type", schema = "events")
class LinkType : AbstractEntity<Short>() {

    @Column(name = "name", columnDefinition = CITEXT)
    var name: String? = null

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "type")
    val links: MutableSet<Link> = mutableSetOf()

}