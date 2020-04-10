package org.polykek.eventsgraph.repository

import org.polykek.eventsgraph.model.FederalSubject
import org.polykek.eventsgraph.model.FederalSubjectType
import org.springframework.data.jpa.repository.JpaRepository

interface FederalSubjectRepository : JpaRepository<FederalSubject, Short> {

    fun findAllByType(federalSubjectType: FederalSubjectType): Set<FederalSubject>
}