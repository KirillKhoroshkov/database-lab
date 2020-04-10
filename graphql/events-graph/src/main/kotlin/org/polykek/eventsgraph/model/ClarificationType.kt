package org.polykek.eventsgraph.model

import org.polykek.eventsgraph.type.CITEXT
import javax.persistence.*

@Entity
@Table(name = "clarification_type", schema = "events")
class ClarificationType(

        @Column(name = "name", columnDefinition = CITEXT)
        val name: String,

        @Column(name = "abbreviation", columnDefinition = CITEXT)
        val abbreviation: String,

        @Column(name = "output_order")
        var outputOrder: Short

) : AbstractPersistableEntity<Short>() {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private var _id: Short? = null

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "type")
    var clarifications: MutableSet<Clarification> = mutableSetOf()

    override fun getId(): Short? = _id
}