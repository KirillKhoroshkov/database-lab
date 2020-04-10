package org.polykek.eventsgraph.model

import org.polykek.eventsgraph.type.CITEXT
import javax.persistence.*

@Entity
@Table(name = "clarification", schema = "events")
class Clarification(

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "place_id")
        val place: Place,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "clarification_type_id")
        val type: ClarificationType,

        @Column(name = "value", columnDefinition = CITEXT)
        val value: String

) : AbstractPersistableEntity<Int>() {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private var _id: Int? = null

    @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinTable(
            schema = "events",
            name = "location_clarification",
            joinColumns = [JoinColumn(name = "clarification_id")],
            inverseJoinColumns = [JoinColumn(name = "location_id")]
    )
    val locations: MutableSet<Location> = mutableSetOf()

    override fun getId(): Int? = _id
}