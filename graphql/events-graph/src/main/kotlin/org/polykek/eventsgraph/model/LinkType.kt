package org.polykek.eventsgraph.model

import org.polykek.eventsgraph.type.CITEXT
import javax.persistence.*

@Entity
@Table(name = "link_type", schema = "events")
class LinkType(

        @Column(name = "name", columnDefinition = CITEXT)
        val name: String

) : AbstractPersistableEntity<Short>() {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private var _id: Short? = null

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "type")
    val links: MutableSet<Link> = mutableSetOf()

    override fun getId(): Short? = _id
}