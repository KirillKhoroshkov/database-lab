package org.polykek.events.model

import org.polykek.events.type.CITEXT
import javax.persistence.*

@Entity
@Table(name = "federal_subject", schema = "events")
class FederalSubject : AbstractEntity<Short>() {

    @Column(name = "name", columnDefinition = CITEXT)
    var name: String? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "federal_subject_type_id")
    var type: FederalSubjectType? = null

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "federalSubject")
    val timezones: MutableSet<FederalSubjectTimezone> = mutableSetOf()

}