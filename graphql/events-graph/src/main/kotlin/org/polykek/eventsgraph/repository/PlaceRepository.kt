package org.polykek.eventsgraph.repository

import org.polykek.eventsgraph.model.Locality
import org.polykek.eventsgraph.model.Place
import org.polykek.eventsgraph.model.PlaceType
import org.springframework.data.jpa.repository.JpaRepository

interface PlaceRepository : JpaRepository<Place, Int> {

    fun findAllByLocality(locality: Locality): Set<Place>

    fun findAllByType(placeType: PlaceType): Set<Place>
}