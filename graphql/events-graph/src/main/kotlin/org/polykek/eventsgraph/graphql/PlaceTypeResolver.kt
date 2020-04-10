package org.polykek.eventsgraph.graphql

import com.coxautodev.graphql.tools.GraphQLResolver
import org.polykek.eventsgraph.model.*
import org.polykek.eventsgraph.repository.PlaceRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class PlaceTypeResolver : GraphQLResolver<PlaceType> {

    @Autowired
    private lateinit var placeRepository: PlaceRepository

    fun getPlaces(placeType: PlaceType): Set<Place> {
        return placeRepository.findAllByType(placeType)
    }
}