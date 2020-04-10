package org.polykek.events.model

import org.polykek.events.type.CITEXT
import javax.persistence.*

@Entity
@Table(name = "clarification_type", schema = "events")
class ClarificationType : AbstractEntity<Short>() {

    @Column(name = "name", columnDefinition = CITEXT)
    var name: String? = null

    @Column(name = "abbreviation", columnDefinition = CITEXT)
    var abbreviation: String? = null

    @Column(name = "output_order")
    var outputOrder: Short? = null

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "type")
    var clarifications: MutableSet<Clarification> = mutableSetOf()

}