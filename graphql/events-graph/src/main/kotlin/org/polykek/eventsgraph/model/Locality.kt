package org.polykek.eventsgraph.model

import org.polykek.eventsgraph.type.CITEXT
import javax.persistence.*

@Entity
@Table(name = "locality", schema = "events")
class Locality(

        @Column(name = "name", columnDefinition = CITEXT)
        var name: String,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "locality_type_id")
        var type: LocalityType,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "federal_subject_timezone_id")
        var federalSubjectTimezone: FederalSubjectTimezone

) : AbstractPersistableEntity<Int>() {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private var _id: Int? = null

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "locality")
    val places: MutableSet<Place> = mutableSetOf()

    override fun getId(): Int? = _id
}