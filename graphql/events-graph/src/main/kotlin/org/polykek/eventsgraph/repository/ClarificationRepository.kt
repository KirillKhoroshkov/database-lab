package org.polykek.eventsgraph.repository

import org.polykek.eventsgraph.model.Clarification
import org.polykek.eventsgraph.model.ClarificationType
import org.polykek.eventsgraph.model.Location
import org.polykek.eventsgraph.model.Place
import org.springframework.data.jpa.repository.JpaRepository

interface ClarificationRepository : JpaRepository<Clarification, Int> {

    fun findAllByType(clarificationType: ClarificationType): Set<Clarification>

    fun findAllByLocationsContains(location: Location): Set<Clarification>

    fun findAllByPlace(place: Place): Set<Clarification>
}