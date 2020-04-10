package org.polykek.eventsgraph.model

import org.polykek.eventsgraph.type.INTERVAL
import org.hibernate.annotations.ColumnTransformer
import javax.persistence.*

@Entity
@Table(name = "federal_subject_timezone", schema = "events")
class FederalSubjectTimezone(

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "federal_subject_id")
        val federalSubject: FederalSubject,

        @Column(name = "utc_offset", columnDefinition = INTERVAL)
        @ColumnTransformer(read = "extract(hour from utc_offset)", write = "(?::text||' hours')::interval")
        val utcOffset: Int

) : AbstractPersistableEntity<Short>() {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private var _id: Short? = null

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "federalSubjectTimezone")
    val localities: MutableSet<Locality> = mutableSetOf()

    override fun getId(): Short? = _id
}