package org.polykek.eventsgraph.repository

import org.polykek.eventsgraph.model.PlaceType
import org.springframework.data.jpa.repository.JpaRepository

interface PlaceTypeRepository : JpaRepository<PlaceType, Short>