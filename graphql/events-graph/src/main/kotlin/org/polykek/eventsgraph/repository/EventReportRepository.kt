package org.polykek.eventsgraph.repository

import org.polykek.eventsgraph.model.EventReport
import org.springframework.data.jpa.repository.JpaRepository

interface EventReportRepository : JpaRepository<EventReport, Int>