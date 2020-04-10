package org.polykek.eventsgraph.repository

import org.polykek.eventsgraph.model.Clarification
import org.polykek.eventsgraph.model.Location
import org.polykek.eventsgraph.model.Place
import org.springframework.data.jpa.repository.JpaRepository

interface LocationRepository : JpaRepository<Location, Int> {

    fun findAllByClarificationsContains(clarification: Clarification): Set<Location>

    fun findAllByPlace(place: Place): Set<Location>
}