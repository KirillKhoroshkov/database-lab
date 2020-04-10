package org.polykek.eventsgraph.model

import org.polykek.eventsgraph.type.CITEXT
import javax.persistence.*

@Entity
@Table(name = "place", schema = "events")
class Place(

        @Column(name = "name", columnDefinition = CITEXT)
        val name: String,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "place_type_id")
        val type: PlaceType,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "locality_id")
        val locality: Locality

) : AbstractPersistableEntity<Int>() {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private var _id: Int? = null

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "place")
    val locations: MutableSet<Location> = mutableSetOf()

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "place")
    val clarifications: MutableSet<Clarification> = mutableSetOf()

    override fun getId(): Int? = _id
}