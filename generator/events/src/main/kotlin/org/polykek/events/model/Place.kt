package org.polykek.events.model

import org.polykek.events.type.CITEXT
import javax.persistence.*

@Entity
@Table(name = "place", schema = "events")
class Place : AbstractEntity<Int>() {

    @Column(name = "name", columnDefinition = CITEXT)
    var name: String? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_type_id")
    var type: PlaceType? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "locality_id")
    var locality: Locality? = null

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "place")
    val locations: MutableSet<Location> = mutableSetOf()

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "place")
    val clarifications: MutableSet<Clarification> = mutableSetOf()

}