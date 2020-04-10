package org.polykek.eventsgraph.repository

import org.polykek.eventsgraph.model.LocalityType
import org.springframework.data.jpa.repository.JpaRepository

interface LocalityTypeRepository : JpaRepository<LocalityType, Short>