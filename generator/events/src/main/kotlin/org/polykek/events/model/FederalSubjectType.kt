package org.polykek.events.model

import org.polykek.events.type.CITEXT
import javax.persistence.*

@Entity
@Table(name = "federal_subject_type", schema = "events")
class FederalSubjectType : AbstractEntity<Short>() {

    @Column(name = "name", columnDefinition = CITEXT)
    var name: String? = null

    @Column(name = "abbreviation", columnDefinition = CITEXT)
    var abbreviation: String? = null

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "type")
    var federalSubjects: MutableSet<FederalSubject> = mutableSetOf()
}