package org.polykek.eventsgraph.graphql

import com.coxautodev.graphql.tools.GraphQLResolver
import org.polykek.eventsgraph.model.*
import org.polykek.eventsgraph.repository.ClarificationRepository
import org.polykek.eventsgraph.repository.LocationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class PlaceResolver : GraphQLResolver<Place> {

    @Autowired
    private lateinit var locationRepository: LocationRepository

    @Autowired
    private lateinit var clarificationRepository: ClarificationRepository

    fun getType(place: Place): PlaceType {
        return place.type
    }

    fun getLocality(place: Place): Locality {
        return place.locality
    }

    fun getLocations(place: Place): Set<Location> {
        return locationRepository.findAllByPlace(place)
    }

    fun getClarifications(place: Place): Set<Clarification> {
        return clarificationRepository.findAllByPlace(place)
    }
}