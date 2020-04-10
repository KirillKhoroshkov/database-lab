package org.polykek.eventsgraph.repository

import org.polykek.eventsgraph.model.FederalSubject
import org.polykek.eventsgraph.model.FederalSubjectTimezone
import org.springframework.data.jpa.repository.JpaRepository

interface FederalSubjectTimezoneRepository : JpaRepository<FederalSubjectTimezone, Short> {

    fun findAllByFederalSubject(federalSubject: FederalSubject): Set<FederalSubjectTimezone>
}