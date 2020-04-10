package org.polykek.eventsgraph.repository

import org.polykek.eventsgraph.model.Event
import org.springframework.data.jpa.repository.JpaRepository

interface EventRepository : JpaRepository<Event, Int>