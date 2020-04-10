package org.polykek.eventsgraph.repository

import org.polykek.eventsgraph.model.EventCost
import org.springframework.data.jpa.repository.JpaRepository

interface EventCostRepository : JpaRepository<EventCost, Long>