package org.polykek.eventsgraph.repository

import org.polykek.eventsgraph.model.EventDate
import org.springframework.data.jpa.repository.JpaRepository

interface EventDateRepository : JpaRepository<EventDate, Int>