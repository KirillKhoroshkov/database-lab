package org.polykek.events.model

import org.polykek.events.type.INTERVAL
import org.hibernate.annotations.ColumnTransformer
import javax.persistence.*

@Entity
@Table(name = "federal_subject_timezone", schema = "events")
class FederalSubjectTimezone : AbstractEntity<Short>() {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "federal_subject_id")
    var federalSubject: FederalSubject? = null

    @Column(name = "utc_offset", columnDefinition = INTERVAL)
    @ColumnTransformer(read = "extract(hour from utcOffset)", write = "(?::text||' hours')::interval")
    var utcOffset: Int? = null

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "federalSubjectTimezone")
    val localities: MutableSet<Locality> = mutableSetOf()
}