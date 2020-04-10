package org.polykek.eventsgraph.graphql

import com.coxautodev.graphql.tools.GraphQLResolver
import org.polykek.eventsgraph.model.*
import org.polykek.eventsgraph.repository.ClarificationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class LocationResolver : GraphQLResolver<Location> {

    @Autowired
    private lateinit var clarificationRepository: ClarificationRepository

    fun getPlace(location: Location): Place {
        return location.place
    }

    fun getClarifications(location: Location): Set<Clarification> {
        return clarificationRepository.findAllByLocationsContains(location)
    }
}