package org.polykek.events.model

import org.polykek.events.type.CITEXT
import javax.persistence.*

@Entity
@Table(name = "clarification", schema = "events")
class Clarification : AbstractEntity<Int>() {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    var place: Place? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clarification_type_id")
    var type: ClarificationType? = null

    @Column(name = "value", columnDefinition = CITEXT)
    var value: String? = null

    @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinTable(
            schema = "events",
            name = "location_clarification",
            joinColumns = [JoinColumn(name = "clarification_id")],
            inverseJoinColumns = [JoinColumn(name = "location_id")]
    )
    val locations: MutableSet<Location> = mutableSetOf()
}