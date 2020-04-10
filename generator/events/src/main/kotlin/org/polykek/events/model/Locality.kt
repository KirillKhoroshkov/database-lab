package org.polykek.events.model

import org.polykek.events.type.CITEXT
import javax.persistence.*

@Entity
@Table(name = "locality", schema = "events")
class Locality : AbstractEntity<Int>() {

    @Column(name = "name", columnDefinition = CITEXT)
    var name: String? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "locality_type_id")
    var type: LocalityType? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "federal_subject_timezone_id")
    var federalSubjectTimezone: FederalSubjectTimezone? = null

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "locality")
    var places: MutableSet<Place> = mutableSetOf()

}