package org.polykek.eventsgraph.graphql

import com.coxautodev.graphql.tools.GraphQLResolver
import org.polykek.eventsgraph.model.*
import org.polykek.eventsgraph.repository.LocationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ClarificationResolver : GraphQLResolver<Clarification> {

    @Autowired
    private lateinit var locationRepository: LocationRepository

    fun getPlace(clarification: Clarification): Place {
        return clarification.place
    }

    fun getType(clarification: Clarification): ClarificationType {
        return clarification.type
    }

    fun getLocations(clarification: Clarification): Set<Location> {
        return locationRepository.findAllByClarificationsContains(clarification)
    }
}