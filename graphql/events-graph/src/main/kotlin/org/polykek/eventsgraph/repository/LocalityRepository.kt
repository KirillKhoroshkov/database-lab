package org.polykek.eventsgraph.repository

import org.polykek.eventsgraph.model.FederalSubjectTimezone
import org.polykek.eventsgraph.model.Locality
import org.polykek.eventsgraph.model.LocalityType
import org.springframework.data.jpa.repository.JpaRepository

interface LocalityRepository : JpaRepository<Locality, Int> {

    fun findAllByFederalSubjectTimezone(federalSubjectTimezone: FederalSubjectTimezone): Set<Locality>

    fun findAllByType(localityType: LocalityType): Set<Locality>
}