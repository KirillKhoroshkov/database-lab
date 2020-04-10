package org.polykek.eventsgraph.repository

import org.polykek.eventsgraph.model.EventDateReport
import org.springframework.data.jpa.repository.JpaRepository

interface EventDateReportRepository : JpaRepository<EventDateReport, Int>