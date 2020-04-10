package org.polykek.eventsgraph.graphql

import com.coxautodev.graphql.tools.GraphQLResolver
import org.polykek.eventsgraph.model.*
import org.polykek.eventsgraph.repository.PlaceRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class LocalityResolver : GraphQLResolver<Locality> {

    @Autowired
    private lateinit var placeRepository: PlaceRepository

    fun getType(locality: Locality): LocalityType {
        return locality.type
    }

    fun getFederalSubjectTimezone(locality: Locality): FederalSubjectTimezone {
        return locality.federalSubjectTimezone
    }

    fun getPlaces(locality: Locality): Set<Place> {
        return placeRepository.findAllByLocality(locality)
    }
}