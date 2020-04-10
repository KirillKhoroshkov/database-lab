package org.polykek.eventsgraph.model

import org.polykek.eventsgraph.type.CITEXT
import javax.persistence.*

@Entity
@Table(name = "federal_subject", schema = "events")
class FederalSubject(

        @Column(name = "name", columnDefinition = CITEXT)
        val name: String,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "federal_subject_type_id")
        val type: FederalSubjectType

) : AbstractPersistableEntity<Short>() {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private var _id: Short? = null

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "federalSubject")
    val timezones: MutableSet<FederalSubjectTimezone> = mutableSetOf()

    override fun getId(): Short? = _id
}