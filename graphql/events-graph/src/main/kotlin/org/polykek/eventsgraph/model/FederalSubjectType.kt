package org.polykek.eventsgraph.model

import org.polykek.eventsgraph.type.CITEXT
import javax.persistence.*

@Entity
@Table(name = "federal_subject_type", schema = "events")
class FederalSubjectType(

        @Column(name = "name", columnDefinition = CITEXT)
        val name: String,

        @Column(name = "abbreviation", columnDefinition = CITEXT)
        val abbreviation: String

) : AbstractPersistableEntity<Short>() {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private var _id: Short? = null

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "type")
    var federalSubjects: MutableSet<FederalSubject> = mutableSetOf()

    override fun getId(): Short? = _id
}